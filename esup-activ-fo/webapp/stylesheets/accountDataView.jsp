<%@include file="_include.jsp"%>

<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}">
	<%@include file="_includeScript.jsp"%>
	<script type="text/javascript" src="/media/scripts/block.js"></script>	
	<%@include file="_navigation.jsp"%>
	
	<e:section value="#{msgs['DATACHANGE.DATACHANGE.TITLE']}"  />
   	
	<t:div styleClass="secondStepImage2fleches">
	<t:htmlTag styleClass="processSteps" value="ul">
	    <t:htmlTag styleClass="homeStep" value="li"><t:graphicImage title="Accueil" value="/media/images/home.jpg"  style="border: 0;cursor:pointer;" onclick="simulateLinkClick('restart:restartButton');"/></t:htmlTag>		
		<t:htmlTag styleClass="firstImage2" value="li"><t:commandLink styleClass="commandLink" onclick="simulateLinkClick('accountForm:preview');"><t:outputText escape="false" value="#{msgs['DATACHANGE.MODIFICATION.TEXT']}"/></t:commandLink></t:htmlTag>
		<t:htmlTag styleClass="currentTab" value="li"><t:outputText escape="false" value="#{msgs['DATACHANGE.DISPLAY.TEXT']}"/></t:htmlTag>
	</t:htmlTag>
	</t:div>
	
	<e:paragraph escape="false" value="#{msgs['PERSOINFO.MESSAGE.CHANGE.SUCCESSFULL']}"/>
	<e:paragraph escape="false" value="#{msgs['DATACHANGE.DISPLAY.TOP']}"/>
	
	<div class="mainBlock">
	
	 <t:dataList value="#{accountController.beanData}" var="category">
	  
	 
      <t:htmlTag value="h2" styleClass="expand" rendered="#{category.access}" ><h:outputText value="#{msgs[category.title]}"/></t:htmlTag>
	 
	  <t:div styleClass="collapse" >
	   <h:dataTable value="#{category.profilingListBeanField}" rendered="#{category.access&&#beanfield.value!=''}" var="beanfield" columnClasses="viewCol1,viewCol2">	  
	   <h:column>						
		  <t:outputText styleClass="labeltexttop#{beanfield.size>1}" value="#{msgs[beanfield.key]}" rendered="#{beanfield.value!=''}"/>
	   </h:column>
	   <h:column >    
	   <t:div styleClass="portlet-section-text" rendered="#{sub.value!=''&&beanfield.fieldType=='link'}">   
	    	<h:outputText escape="false" converter="#{beanfield.converter}" rendered="#{beanfield.fieldType=='link'}" value="#{msgs[beanfield.value]}"/>
	    </t:div>	 	    
        <t:dataList value="#{beanfield.values}" var="sub" rendered="#{beanfield.value!=''&&sub.value!=''}">
		    <t:div styleClass="portlet-section-text" rendered="#{sub.value!=''&&!sub.convertedValue}">
			    <h:outputText value="#{sub.value}" converter="#{beanfield.converter}" rendered="#{sub.value!=''}"/>			  	
	        </t:div>
         </t:dataList>                 
  		</h:column>  								
	  </h:dataTable>
	 </t:div>	 	 
    </t:dataList>      
  </div>		
	<h:form id="accountForm" style="display:none;" >
		<e:commandButton id="preview" value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushChangeInfoPerso}"/>
	</h:form>
	<h:form>
	  <e:commandButton value="#{msgs['APPLICATION.BUTTON.RESTART']}" action="#{exceptionController.restart}" />
	</h:form>
	
	<h:form id="restart" style="display:none;"  >
	  <e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}" action="#{exceptionController.restart}" />
	</h:form>

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>