package cn.itcast;

import cn.itcast.pojo.TbItem;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-solr.xml")
public class TestTemplate {


    @Autowired
    private SolrTemplate solrTemplate;

    @Test
    public void testAdd() {
        TbItem item = new TbItem();
        item.setId(11L);
        item.setTitle("荣耀METE10");
        item.setBrand("荣耀");
        item.setCategory("手机");
        item.setGoodsId(1L);
        item.setSeller("华为旗舰店");
        item.setPrice(new BigDecimal(3000.11));

        solrTemplate.saveBean(item);
        solrTemplate.commit();
    }

    @Test
    public void findById() {
        TbItem byId = solrTemplate.getById(1L, TbItem.class);
        System.out.println(byId);
    }

    @Test
    public void delete() {
        solrTemplate.deleteById("1");

        solrTemplate.commit();
    }

    @Test
    public void AddList() {

        List<TbItem> list=new ArrayList<TbItem>();

        for (int i = 0; i <100 ; i++) {
            TbItem item = new TbItem();
            item.setId(i+1L);
            item.setTitle("荣耀METE10"+i);
            item.setBrand("荣耀");
            item.setCategory("手机");
            item.setGoodsId(1L);
            item.setSeller("华为旗舰店");
            item.setPrice(new BigDecimal(1.11+i));
            list.add(item);

        }

        solrTemplate.saveBeans(list);
        solrTemplate.commit();

    }

    @Test
    public void pageQuery(){
        Query query = new SimpleQuery("*:*");
        query.setOffset(1);
        query.setRows(4);
        ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
        System.out.println("总条数："+page.getTotalElements());

        List<TbItem> content = page.getContent();
        for (TbItem tbItem : content) {
            System.out.println(tbItem);
        }
    }

    @Test
    public void pageQueryMutil(){
        Query query = new SimpleQuery("*:*");
        Criteria criteria = new Criteria("item_category").contains("手机");
       query.addCriteria(criteria);
        query.setOffset(1);
        query.setRows(4);
        ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
        System.out.println("总条数："+page.getTotalElements());

        List<TbItem> content = page.getContent();
        for (TbItem tbItem : content) {
            System.out.println(tbItem);
        }
    }

    @Test
    public void deleteAll(){
        SimpleQuery query = new SimpleQuery("*:*");
       solrTemplate.delete(query);
       solrTemplate.commit();
    }

}
