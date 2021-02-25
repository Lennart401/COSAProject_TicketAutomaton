package de.leuphana.swa.documentsystem.behaviour.service.event;

public interface ManageableEventService {
	void addManageableEventListener(ManageableEventListener manageableEventListener);
	void removeManageableEventListener(ManageableEventListener manageableEventListener);
}