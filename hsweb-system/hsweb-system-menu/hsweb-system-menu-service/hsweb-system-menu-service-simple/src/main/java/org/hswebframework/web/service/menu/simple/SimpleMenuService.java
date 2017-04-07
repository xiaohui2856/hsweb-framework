/*
 *  Copyright 2016 http://www.hswebframework.org
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package org.hswebframework.web.service.menu.simple;

import org.hswebframework.web.dao.menu.MenuDao;
import org.hswebframework.web.entity.menu.MenuEntity;
import org.hswebframework.web.id.IDGenerator;
import org.hswebframework.web.service.AbstractTreeSortService;
import org.hswebframework.web.service.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 */
@Service("menuService")
@CacheConfig(cacheNames = CacheConstants.MENU_CACHE_NAME)
public class SimpleMenuService
        extends AbstractTreeSortService<MenuEntity<MenuEntity>, String>
        implements MenuService {

    private MenuDao menuDao;

    @Override
    protected IDGenerator<String> getIDGenerator() {
        return IDGenerator.MD5;
    }

    @Autowired
    public void setMenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    @Override
    public MenuDao getDao() {
        return menuDao;
    }

    @Override
    @CacheEvict(allEntries = true)
    public int updateByPk(List<MenuEntity<MenuEntity>> data) {
        return super.updateByPk(data);
    }

    @Override
    @CacheEvict(allEntries = true)
    public String insert(MenuEntity<MenuEntity> entity) {
        return super.insert(entity);
    }

    @Override
    @CacheEvict(allEntries = true)
    public int deleteByPk(String id) {
        return super.deleteByPk(id);
    }

    @Override
    @Cacheable(key = "'permission-ids:'+#permissionId==null?'0':#permissionId.hashCode()")
    public List<MenuEntity<MenuEntity>> getByPermissionId(List<String> permissionId) {
        return createQuery().noPaging().where().in("permissionId", permissionId).list();
    }

    @Override
    @Cacheable(key = "'permission-id:'+#permissionId")
    public MenuEntity<MenuEntity> getByPermissionId(String permissionId) {
        return createQuery().noPaging().where().is("permissionId", permissionId).single();
    }
}