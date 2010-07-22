<%@include file="_include.jsp"%>
<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}" >
	<%@include file="_navigation.jsp"%>
	
	<t:div rendered="#{accountController.reinit == false}">
		<e:section value="#{msgs['ENABLED.TITLE']}" />
		<e:messages />
		<e:paragraph value="#{msgs['ENABLED.TEXT.TOP']}" />


		<e:paragraph escape="false" value="#{msgs['ENABLED.TEXT.UNAUTHENTICATED']}">
		  <f:param value="#{accountController.currentAccount.id}" />
		  <f:param value="#{accountController.currentAccount.mail}" />
		</e:paragraph>
	</t:div>
	<t:div rendered="#{accountController.reinit == true}">
		<e:section value="#{msgs['REINITIALIZED.TITLE']}" />
		<e:messages />
		<e:paragraph value="#{msgs['REINITIALIZED.TEXT.TOP']}" />

		<e:paragraph escape="false" value="#{msgs['REINITIALIZED.TEXT.UNAUTHENTICATED']}">
		  <f:param value="#{accountController.currentAccount.id}" />
		  <f:param value="#{accountController.currentAccount.mail}" />
		 </e:paragraph>
	 </t:div>
	 
	 	 
	 <e:subSection value="#{msgs['ENABLED.SUBTITLE.ESUPACCESS']}" />
	 <e:paragraph escape="false" value="#{msgs['ENABLED.TEXT.ESUPURL']}" />
	 
<% /* @include file="_debug.jsp" */ %>
</e:page>