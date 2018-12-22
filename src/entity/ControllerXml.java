package entity;

import java.util.ArrayList;
import java.util.List;

//一个ControllerXml对象包含一个ActionXml对象列表
public class ControllerXml {	
	private List<ActionXml> actionList;
	
	
	public ActionXml getActionByName(String actionName){		
		for(ActionXml actionXml : actionList){
			if(actionName.equals(actionXml.getName())){
				return actionXml;
			}
		}
		return null;
	}
	
	public ControllerXml() {
		// TODO Auto-generated constructor stub
		actionList = new ArrayList<ActionXml>();
	}

	public List<ActionXml> getActionList() {
		return actionList;
	}

	public void setActionList(List<ActionXml> actionList) {
		for(ActionXml actionXml : actionList){			
			this.actionList.add(actionXml);
		}
	}
}
