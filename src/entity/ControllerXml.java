package entity;

import java.util.ArrayList;
import java.util.List;

public class ControllerXml {
	private List<ActionXml> actionList;
	
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
