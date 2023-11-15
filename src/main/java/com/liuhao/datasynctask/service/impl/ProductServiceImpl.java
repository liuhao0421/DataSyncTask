package com.liuhao.datasynctask.service.impl;

import com.liuhao.datasynctask.entity.ProductEntity;
import com.liuhao.datasynctask.mapper.ProductMapper;
import com.liuhao.datasynctask.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuhao
 * @since 2023-11-15
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductEntity> implements ProductService {

}
