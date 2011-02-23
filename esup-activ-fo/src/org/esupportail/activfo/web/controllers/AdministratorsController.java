/**
 * ESUP-Portail esup-activ-fo - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-activ-fo
 */
package org.esupportail.activfo.web.controllers; 

import java.util.List;

import org.esupportail.activfo.domain.beans.User;
import org.esupportail.commons.exceptions.UserNotFoundException;
import org.esupportail.commons.web.beans.Paginator;
import org.esupportail.commons.web.controllers.LdapSearchCaller;

/**
 * Bean to present and manage administrators.
 */
public class AdministratorsController extends AbstractContextAwareController implements LdapSearchCaller {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 5115437075806072781L;

	/**
	 * The id of the user to give administrator privileges.
	 */
	private String ldapUid;

	/**
	 * The user of whom the administrator's privileges will be revoked.
	 */
	private User userToDelete;
	
	

    /**
     * The paginator.
     */
    private Paginator<User> paginator;
    
	/**
	 * Bean constructor.
	 */
	public AdministratorsController() {
		super();
	}

	/**
	 * @see org.esupportail.activfo.web.controllers.AbstractContextAwareController#afterPropertiesSetInternal()
	 */
	@Override
	public void afterPropertiesSetInternal() {
		super.afterPropertiesSetInternal();
		
	}

	/**
	 * @see org.esupportail.activfo.web.controllers.AbstractContextAwareController#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		ldapUid = null;
		userToDelete = null;
		paginator = getDomainService().getAdminPaginator();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + "#" + hashCode() + "[ldapUid=[" + ldapUid 
		+ "], userToDelete=" + userToDelete + ", paginator=" + paginator
		+ "]";
	}

	/**
	 * @return true if the current user is allowed to access the view.
	 */
	public boolean isPageAuthorized() {
		User currentUser = getCurrentUser();
		if (currentUser == null) {
			return false;
		}
		return getDomainService().userCanViewAdmins(currentUser);
	}

	/**
	 * JSF callback.
	 * @return A String.
	 */
	public String enter() {
		if (!isPageAuthorized()) {
			addUnauthorizedActionMessage();
			return null;
		}
		return "navigationAdministrators";
	}

	/**
	 * @return true if the current user can add an admin.
	 */	
	public boolean isCurrentUserCanAddAdmin() {
		return getDomainService().userCanAddAdmin(getCurrentUser());
	}

	/**
	 * @return true if the current user can delete an admin.
	 */	
	public boolean isCurrentUserCanDeleteAdmin() {
		return getDomainService().userCanDeleteAdmin(getCurrentUser(), userToDelete);
	}

	/**
	 * @return the paginator.
	 */
	public Paginator<User> getPaginator() {
		return paginator;
	}

	/**
	 * @return the LDAP statistics.
	 */
	public List <String> getLdapStatistics() {
		return null;
	}

	/**
	 * @return a String.
	 */
	public String addAdmin() {
		if (!isCurrentUserCanAddAdmin()) {
			addUnauthorizedActionMessage();
			return null;
		}
		try {
			User user = getDomainService().getUser(ldapUid);
			if (user.getAdmin()) {
				addErrorMessage(
						null, "ADMINISTRATORS.MESSAGE.USER_ALREADY_ADMINISTRATOR", ldapUid);
				return null;
			}
			getDomainService().addAdmin(user);
			ldapUid = "";
			addInfoMessage(null, "ADMINISTRATORS.MESSAGE.ADMIN_ADDED", user.getDisplayName(), user.getId());
			return "adminAdded";
		} catch (UserNotFoundException e) {
			addWarnMessage("ldapUid", "_.MESSAGE.USER_NOT_FOUND", ldapUid);
			return null;
		}
	}
	
	/**
	 * @return a String.
	 */
	public String confirmDeleteAdmin() {
		if (!isCurrentUserCanDeleteAdmin()) {
			addUnauthorizedActionMessage();
			return null;
		}
		getDomainService().deleteAdmin(userToDelete);
		addInfoMessage(null, "ADMINISTRATORS.MESSAGE.ADMIN_DELETED", 
				userToDelete.getDisplayName(), userToDelete.getId());
		return "adminDeleted";
	}

	/**
	 * @param userToDelete the userToDelete to set
	 */
	public void setUserToDelete(final User userToDelete) {
		this.userToDelete = userToDelete;
	}

	/**
	 * @return the userToDelete
	 */
	public User getUserToDelete() {
		return userToDelete;
	}

	/**
	 * @return the ldapUid
	 */
	public String getLdapUid() {
		return ldapUid;
	}

	/**
	 * @param ldapUid the ldapUid to set
	 */
	public void setLdapUid(final String ldapUid) {
		this.ldapUid = ldapUid;
	}

}