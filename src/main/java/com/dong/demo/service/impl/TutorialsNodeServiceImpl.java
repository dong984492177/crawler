package com.dong.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.demo.mapper.TutorialsNodeMapper;
import com.dong.demo.model.TutorialsNode;
import com.dong.demo.service.TutorialsNodeService;
import com.dong.demo.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author DONG
 * @description 针对表【tutorials_node(爬虫一些教程 需要存放的路径信息)】的数据库操作Service实现
 * @createDate 2021-12-19 03:23:04
 */
@Service
@Slf4j
public class TutorialsNodeServiceImpl extends ServiceImpl<TutorialsNodeMapper, TutorialsNode>
        implements TutorialsNodeService {
    @Autowired
    RedisUtils redisUtils;

    @Autowired
    TutorialsNodeMapper tutorialsNodeMapper;

    @Override
    public TutorialsNode getByName(int id, String name) {
        TutorialsNode tutorialsNode;
        String key = "tutorialsMapping:hash" + id;
        String tutorialsNodeStringJson = (String) redisUtils.hmGet(key, name);
        //String tutorialsNodeStringJson = null;
        if (tutorialsNodeStringJson == null) {
            tutorialsNode = getDbByName(id, name);
            if (tutorialsNode == null) {
                return null;
            }
        } else {
            tutorialsNode = JSON.parseObject(tutorialsNodeStringJson, TutorialsNode.class);
        }
        if (tutorialsNode == null) {
            log.info("tutorialsNode 没有数据 ");
            return null;
        }
        return tutorialsNode;
    }

    @Override
    public TutorialsNode getDbByName(int id, String name) {
        String key = "tutorialsMapping:hash" + id;
        TutorialsNode tutorialsNode;
        tutorialsNode = tutorialsNodeMapper.selectOne(new QueryWrapper<TutorialsNode>().eq("name", name).eq("crawle_id", id));
        if (tutorialsNode == null) {
            log.info("tutorialsNode 没有数据 ");
            return null;
        } else {
            redisUtils.hmSet(key, name, JSON.toJSONString(tutorialsNode));
        }
        return tutorialsNode;
    }

    @Override
    public boolean saveOrUpdateByName(TutorialsNode tutorialsNode) {
        boolean boon = saveOrUpdate(tutorialsNode, new UpdateWrapper<TutorialsNode>()
                .eq("crawle_id", tutorialsNode.getCrawleId())
                .eq("name", tutorialsNode.getName())
        );
        if (boon) {
            String key = "tutorialsMapping:hash" + tutorialsNode.getCrawleId();
            redisUtils.hmSet(key, tutorialsNode.getName(), JSON.toJSONString(tutorialsNode));
        }
        return boon;
    }


    @Override
    public List<TutorialsNode> getDbByCrawleId(int id) {
        String key = "tutorialsMapping:hash" + id;
        List<TutorialsNode> tutorialsNodeList = list(new QueryWrapper<TutorialsNode>().eq("crawle_id", id).ne("parent_id", -1).orderByAsc("id"));
        if (tutorialsNodeList.size() == 0) {
            log.info("tutorialsNode 没有数据 ");
            return null;
        } else {
            for (TutorialsNode tutorialsNode : tutorialsNodeList) {
                redisUtils.hmSet(key, tutorialsNode.getName(), JSON.toJSONString(tutorialsNode));
            }
        }
        return tutorialsNodeList;
    }

    @Override
    public List<TutorialsNode> getByCrawleId(int id) {
        String key = "tutorialsMapping:hash" + id;
        List<TutorialsNode> tutorialsNodeList;
        Set<Object> tutorialsNodeSet = redisUtils.hmGetAll(key);
        //String tutorialsNodeStringJson = null;
        if (tutorialsNodeSet.size() == 0) {
            tutorialsNodeList = getDbByCrawleId(id);
        } else {
            tutorialsNodeList = new ArrayList<>();
            for (Object o : tutorialsNodeSet) {
                String tutorialsNodeStr = (String) redisUtils.hmGet(key, o);
                tutorialsNodeList.add(JSON.parseObject(tutorialsNodeStr.toString(), TutorialsNode.class));
            }
        }
        if (tutorialsNodeList == null) {
            log.info("tutorialsNode 没有数据 ");
            return null;
        }
        return tutorialsNodeList;
    }


}




