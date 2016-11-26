import java.lang.reflect.Field;
import java.util.Date;

import org.junit.Test;




/**		
 * <p>Title: MybatisDaoGenerator </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年10月29日 下午2:55:32	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年10月29日 下午2:55:32</p>
 * <p>修改备注：</p>
 */
public class MybatisDaoGenerator
{
    @Test
    public void testGenerateFindBy()
    {
//        System.out.println(generateFindBy(PageConfig.class));
//        System.out.println(generateDtoProperites(PageConfig.class));
//        System.out.println(generateSelect(PageConfigDto.class));
    }
    
    /**
     * 根据pojo生成dto	 
     * @author 朱振	
     * @date 2015年11月6日 下午2:20:38	
     * @version 1.0
     * @param clazz
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月6日 下午2:20:38</p>
     * <p>修改备注：</p>
     */
    public String generateDtoProperites(Class<?> clazz)
    {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder result = new StringBuilder();
        for(Field field:fields)
        {
            result.append("@Getter\n");
            result.append("@Setter\n");
            result.append("private ");
            result.append(field.getType().getSimpleName());
            result.append(" ");
            result.append(field.getName());
            result.append(";");
            result.append("\n");
            result.append("\n");
        }
        
        return result.toString();
    }
    
    /**
     * 将第一个英文字母大写<BR> 
     * @author 朱振	
     * @date 2015年11月6日 下午2:16:39	
     * @version 1.0
     * @param str
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月6日 下午2:16:39</p>
     * <p>修改备注：</p>
     */
    public String toFirst(String str)
    {
        StringBuilder sb = new StringBuilder();
        sb.append((str.charAt(0)+"").toUpperCase());
        sb.append(str.substring(1));
        
        return sb.toString();
    }
    
    /**
     * 生成findBy<BR>	 
     * @author 朱振	
     * @date 2015年11月6日 下午2:15:56	
     * @version 1.0
     * @param clazz
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月6日 下午2:15:56</p>
     * <p>修改备注：</p>
     */
    public String generateFindBy(Class<?> clazz)
    {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder result = new StringBuilder();
        for(Field field:fields)
        {
            result.append("//");
            result.append(field.getName());
            result.append("\n");
            
            if(field.getType() == Integer.class ||field.getType() == Long.class || field.getType() == Float.class)
            {
                //这里处理Integer,Long,Float类型
                if("stat".equals(field.getName()))
                {
                    result.append("if(null == param.get");
                }
                else
                {
                    result.append("if(null != param.get");
                }
                
                result.append(toFirst(field.getName()));
                result.append("())");
            }
            else if(field.getType()==Date.class)
            {
                //date类型暂不处理
                continue;
            }
            else
            {
                //这里处理String类型
                result.append("if(!StringUtils.isEmpty(param.get");
                result.append(toFirst(field.getName()));
                result.append("()))");
            }
            
            result.append("\n");
            result.append("{");
            result.append("\n");
            result.append("  criteria.and");
            result.append(toFirst(field.getName()));
            result.append("EqualTo(");
            
            if("stat".equals(field.getName()))
            {
                result.append("DataStatus.ENABLED);");
            }
            else
            {
                result.append("param.get");
                result.append(toFirst(field.getName()));
                result.append("());");
            }
            result.append("\n");
            result.append("}");
            result.append("\n");
           
        }
        
        return result.toString();
    }
    
    /**
     * 生成select<BR> 
     * @author 朱振	
     * @date 2015年11月6日 下午2:13:19	
     * @version 1.0
     * @param clazz
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月6日 下午2:13:19</p>
     * <p>修改备注：</p>
     */
    public String generateSelect(Class<?> clazz)
    {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder result = new StringBuilder("select ");
        for(Field field:fields)
        {
            result.append(underscoreName(field.getName()).toLowerCase());
            result.append(" as ");
            result.append(field.getName());
            result.append(",");
            result.append("\n");
        }
        
        return result.toString();
    }
    
    /**
     * 将大写字母改为下划线<BR>	 
     * @author 朱振	
     * @date 2015年11月6日 下午2:18:10	
     * @version 1.0
     * @param name
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月6日 下午2:18:10</p>
     * <p>修改备注：</p>
     */
    public  String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // 将第一个字符处理成大写
            result.append(name.substring(0, 1).toUpperCase());
            // 循环处理其余字符
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接转成大写
                result.append(s.toUpperCase());
            }
        }
        return result.toString();
    }
}

