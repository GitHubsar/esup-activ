package org.esupportail.activfo.dao;

import java.util.List;

import org.esupportail.activfo.domain.beans.User;
import org.esupportail.activfo.domain.beans.VersionManager;
import org.esupportail.commons.services.application.VersionningUtils;
import org.esupportail.commons.web.beans.Paginator;

public class EmptyDaoService implements DaoService {

	private static final long serialVersionUID = 3429875756876395115L;
	
	private static VersionManager versionManager;

	
	
	public void addUser(User user) {
		// TODO Auto-generated method stub

	}

	public void addVersionManager(VersionManager versionManager) {
		// TODO Auto-generated method stub

	}

	public void deleteUser(User user) {
		// TODO Auto-generated method stub

	}

	public Paginator<User> getAdminPaginator() {
		// TODO Auto-generated method stub
		return null;
	}

	public User getUser(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<VersionManager> getVersionManagers() {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateUser(User user) {
		// TODO Auto-generated method stub

	}

	public void updateVersionManager(VersionManager versionManager) {
		// TODO Auto-generated method stub

	}

	public VersionManager getVersionManager() {
		// TODO Auto-generated method stub
		if(versionManager==null)		{
			versionManager = new VersionManager();
			versionManager.setVersion(VersionningUtils.VERSION_0);
		}
		return versionManager;		
	}

}
