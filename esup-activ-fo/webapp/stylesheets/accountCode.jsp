<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>

<e:page stringsVar="msgs" menuItem="account"
	locale="#{sessionController.locale}">
	<%@include file="_navigation.jsp"%>
	<e:section value="#{msgs['CODE.TITLE']}" />
	<e:messages />
		
		
	<e:paragraph escape="false" value="#{msgs['CODE.TEXT.MAILPERSO.TOP']}" rendered="#{accountController.currentAccount.oneChoiceCanal==accountController.accountMailPersoKey}">
		  <f:param value="#{accountController.partialMailPerso}" />
	</e:paragraph>
	
	<e:paragraph escape="false" value="#{msgs['CODE.TEXT.PAGER.TOP']}" rendered="#{accountController.currentAccount.oneChoiceCanal==accountController.accountPagerKey}">
		  <f:param value="#{accountController.partialPager}" />
	</e:paragraph>
	
	<e:paragraph escape="false" value="#{msgs['CODE.TEXT.GEST.TOP']}" rendered="#{accountController.currentAccount.oneChoiceCanal==accountController.accountGestKey}">
		  
	</e:paragraph>
	
	<h:form id="accountForm" rendered="#{sessionController.currentUser == null}">
		<e:panelGrid columns="3">
			<e:outputLabel for="code" value="#{msgs[beanCode.key]}" />
			<e:inputText id="code" value="#{beanCode.value}" required="#{beanCode.required}" validator="#{beanCode.validator.validate}">
			</e:inputText>
		    <h:graphicImage styleClass="helpTip" longdesc="#{msgs[beanCode.help]}" value="/media/help.jpg"  style="border: 0;" rendered="#{beanCode.help!=null}"/>
		</e:panelGrid>
		
		<t:div style="margin-top:30;">
			<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushVerifyCode}" />
		</t:div>
		<e:message for="code" />	
	
	</h:form>
	
	<h:form>
		<e:commandButton value="#{msgs['APPLICATION.BUTTON.RESTART']}"action="#{exceptionController.restart}" />
	</h:form>

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>