package Arbitrator;

import java.util.List;

public class Arbitrator {
	
	
	public interface Behavior {
		public boolean takeControl();
		public void action();
		public void suppress();
	}
	
	private List<Behavior> behaviors;
	
	public Arbitrator(List<Behavior> behaviors) {
		this.behaviors = behaviors;
	}
	
	public void run() {
		Behavior selectedBehavior = this.behaviors.get(this.behaviors.size() - 1);
		while(true) {
			for(int i = this.behaviors.size() - 1; i >= 0; i--) {
				Behavior behavior = this.behaviors.get(i);
				if (behavior.takeControl()) {
					if (selectedBehavior != null && selectedBehavior != behavior) {
						behavior.suppress();
						selectedBehavior = behavior;
					}
				}
			}
			if (selectedBehavior != null)
				selectedBehavior.action();
		}
	}
}
