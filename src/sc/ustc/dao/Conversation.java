package sc.ustc.dao;

import java.util.List;
import java.util.Stack;

import com.sun.xml.internal.bind.v2.model.core.ID;

import entity.orMapping.ClassMapping;
import util.ClassReflector;

/*Conversation 负责完成将对象操作映射为数据表操作，即在 Conversation 中定义数据操作 CRUD 方法，
 * 每个方法将对象操作解释成目标数据库的 DML 或 DDL，通过 JDBC 完成数据持久化*/

//即实现对对象的增、删、查、改功能
public class Conversation {
	private static Configuration configuration;

	public static boolean addObject(Object obj) {
		return false;
	}

	public static boolean deleteObject(Object obj) {
		return false;
	}

	public static Object getObject(Object obj) {
		// 从配置文件中获取到table 和 对象的映射信息				
		configuration = new Configuration();			
		List<ClassMapping> classMappingList = configuration.getClassMappingList();
		String sql = null;

		// 遍历所有的表，对符合要求的表进行操作
		for (ClassMapping clm : classMappingList) {				
			if (obj.getClass().getSimpleName().equals(clm.getClassName())) {
				//因为调用反射时，需要传入完整的包名和类名，所以此处将clm中的类名填为完整的
				clm.setClassName(obj.getClass().getName());				
				//先拿到对象，从而拿到对象的id属性,属性名为clm.getId()的值			
				String id = (String)ClassReflector.getValueByName(obj, clm.getId());				
				
				StringBuilder stringBuilder = new StringBuilder("select * from ");
				stringBuilder.append(clm.getTable());
				stringBuilder.append(" where ");
				stringBuilder.append(clm.getId()+"= \"");
				stringBuilder.append(id + "\"");
				sql = stringBuilder.toString();												
				return new BaseDAO().query(sql, clm);								
			}
		}
		return null;
	}

	public static boolean updateObject(Object obj) {
		return false;
	}
}
