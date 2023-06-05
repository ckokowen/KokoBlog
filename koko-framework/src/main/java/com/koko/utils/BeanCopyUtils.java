package com.koko.utils;

import com.koko.domain.entity.Article;
import com.koko.domain.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-03-11:10
 */
public class BeanCopyUtils {
    /**
     * 拷贝一个对象
     * @param source
     * @param clazz
     * @param <V>
     * @return
     */
    public static <V> V copyBean(Object source,Class<V> clazz){
        //创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            //将属性拷贝到目标对象中
            BeanUtils.copyProperties(source,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 拷贝一个集合的对象
     * @param list
     * @param clazz
     * @param <O>
     * @param <V>
     * @return
     */
    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> clazz){
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }


//    public static void main(String[] args) {
//        Article article = new Article();
//        article.setId(1L);
//        article.setTitle("kk");
//        HotArticleVo vo = BeanCopyUtils.copyBean(article, HotArticleVo.class);
//        System.out.println(vo);
//    }

}
