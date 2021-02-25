package de.leuphana.swa.documentsystem.behaviour.service.event;

import java.util.EventListener;

public interface ManageableEventListener extends EventListener {
	void onManageableCreated(ManageableEvent manageableEvent);
//	void onManageableDeleted(ManageableEvent manageableEvent);
//	void onManageableAdded(ManageableEvent manageableEvent);
//	void onManageableUpdated(ManageableEvent manageableEvent);
//	void onMangeableRequested(ManageableEvent manageableEvent);
}
