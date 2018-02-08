package com.wanxp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.wanxp.util.F;
import com.wanxp.dao.MetaDaoI;
import com.wanxp.model.Tmeta;
import com.wanxp.pageModel.Meta;
import com.wanxp.pageModel.DataGrid;
import com.wanxp.pageModel.PageHelper;
import com.wanxp.service.MetaServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wanxp.util.MyBeanUtils;

@Service
public class MetaServiceImpl extends BaseServiceImpl<Meta> implements MetaServiceI {

	@Autowired
	private MetaDaoI metaDao;

	@Override
	public DataGrid dataGrid(Meta meta, PageHelper ph) {
		List<Meta> ol = new ArrayList<Meta>();
		String hql = " from Tmeta t ";
		DataGrid dg = dataGridQuery(hql, ph, meta, metaDao);
		@SuppressWarnings("unchecked")
		List<Tmeta> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (Tmeta t : l) {
				Meta o = new Meta();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(Meta meta, Map<String, Object> params) {
		String whereHql = "";	
		if (meta != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(meta.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", meta.getTenantId());
			}		
			if (!F.empty(meta.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", meta.getIsdeleted());
			}		
			if (!F.empty(meta.getName())) {
				whereHql += " and t.name = :name";
				params.put("name", meta.getName());
			}		
			if (!F.empty(meta.getSlug())) {
				whereHql += " and t.slug = :slug";
				params.put("slug", meta.getSlug());
			}		
			if (!F.empty(meta.getType())) {
				whereHql += " and t.type = :type";
				params.put("type", meta.getType());
			}		
			if (!F.empty(meta.getDescription())) {
				whereHql += " and t.description = :description";
				params.put("description", meta.getDescription());
			}		
			if (!F.empty(meta.getSort())) {
				whereHql += " and t.sort = :sort";
				params.put("sort", meta.getSort());
			}		
			if (!F.empty(meta.getParent())) {
				whereHql += " and t.parent = :parent";
				params.put("parent", meta.getParent());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(Meta meta) {
		Tmeta t = new Tmeta();
		BeanUtils.copyProperties(meta, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		metaDao.save(t);
	}

	@Override
	public Meta get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tmeta t = metaDao.get("from Tmeta t  where t.id = :id", params);
		Meta o = new Meta();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(Meta meta) {
		Tmeta t = metaDao.get(Tmeta.class, meta.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(meta, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		metaDao.executeHql("update Tmeta t set t.isdeleted = 1 where t.id = :id",params);
		//metaDao.delete(metaDao.get(Tmeta.class, id));
	}

}
