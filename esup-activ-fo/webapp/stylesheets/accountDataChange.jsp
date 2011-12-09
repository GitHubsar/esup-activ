<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>
<script type="text/javascript" src="/media/scripts/block.js"></script>

<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}">
	
	<%@include file="_navigation.jsp"%>
	
	<e:section value="#{msgs['DATACHANGE.DATACHANGE.TITLE']}"  />
   
	<t:div styleClass="firstStepImage2fleches">
	<t:htmlTag styleClass="processSteps" value="ul">
	    <t:htmlTag styleClass="homeStep" value="li"><t:graphicImage title="Accueil" value="/media/images/home.jpg"  style="border: 0;cursor:pointer;" onclick="simulateLinkClick('restart:restartButton');"/></t:htmlTag>		
		<t:htmlTag styleClass="currentTabModification" value="li"><t:outputText escape="false" value="#{msgs['DATACHANGE.MODIFICATION.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="secondStep" value="li"><t:commandLink styleClass="commandLink" onclick="simulateLinkClick('accountForm:next');"><t:outputText escape="false" value="#{msgs['DATACHANGE.DISPLAY.TEXT']}"/></t:commandLink></t:htmlTag>
	</t:htmlTag>
	</t:div>
	<e:messages/>	
	<e:paragraph value="#{msgs['DATACHANGE.TEXT.TOP']}" escape="false"/>

	<f:verbatim>
		<fieldset class="legend">
       	 	<legend><h:outputText value="#{msgs['DATACHANGE.TEXT.LEGEND']}"/></legend>
      		<h:panelGrid columns="2" columnClasses="legendCol1,legendCol2">
      			<t:div><h:graphicImage value="/media/images/help.jpg"/><h:outputText styleClass="portlet-section-text" value=": #{msgs['DATACHANGE.TEXT.LEGEND.HELP']}"/></t:div>    		
      			<t:div><h:graphicImage value="/media/images/redtriangular.jpg"/><h:outputText styleClass="portlet-section-text" value=": #{msgs['DATACHANGE.TEXT.LEGEND.NOTE']}"/></t:div> 
      			<t:div><h:graphicImage value="/media/images/add.png"/><h:outputText styleClass="portlet-section-text" value=": #{msgs['DATACHANGE.TEXT.LEGEND.ADD']}"/></t:div> 
      			<t:div><h:graphicImage value="/media/images/remove.png"/><h:outputText styleClass="portlet-section-text" value=": #{msgs['DATACHANGE.TEXT.LEGEND.REMOVE']}"/></t:div> 
      		</h:panelGrid>
    	</fieldset>		
	</f:verbatim>

	<t:div>
		<t:htmlTag id="AllModifyLink" value="a">			
  			<h:outputText value="#{msgs['DATACHANGE.MODIFICATION.ALL.LINK']}"/>  				
  		</t:htmlTag>
  	</t:div>
	
	<h:form id="accountForm" >
	<div class="mainBlock">
	
	 <t:dataList value="#{accountController.beanData}" var="category">
	  
	 
      <t:htmlTag value="h2" styleClass="expand" rendered="#{category.access}"><h:outputText value="#{msgs[category.title]}"/></t:htmlTag>
	 
	  <t:div styleClass="collapse" >
	   <h:dataTable  value="#{category.profilingListBeanField}" rendered="#{category.access}" var="beanfield" columnClasses="firstColumn,secondColumn,thirdColumn,fourthColumn">
	  
	   <h:column  >
		  <t:outputText styleClass="labeltexttop#{beanfield.size>1}"  value="#{msgs[beanfield.key]}"/>		 
	   </h:column>
	   <h:column >
	    
		<t:dataList value="#{beanfield.values}" var="sub"  >
		    		    
		     <t:div styleClass="#{beanfield.name}output portlet-section-text output" rendered="#{beanfield.fieldType!='selectManyCheckbox'&&beanfield.fieldType!='selectOneRadio'&&!sub.convertedValue}">
			   		<h:outputText value="#{sub.value}" rendered="#{beanfield.fieldType!='selectManyCheckbox'&&beanfield.fieldType!='selectOneRadio'}" converter="#{beanfield.converter}"/>			   		
	        </t:div>
	        
		    <t:div rendered="#{sub.value!=''&&!sub.convertedValue||(sub.value==''&&!beanfield.multiValue)}" style="display:none;" styleClass="#{beanfield.name}show">
			    <h:inputText value="#{sub.value}"  disabled="#{beanfield.disable}" converter="#{beanfield.converter}" validator="#{beanfield.validator.validate}"  required="#{beanfield.required}" size="35" rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator!=null&&(sub.value!=''||(sub.value==''&&!beanfield.multiValue))}" immediate="true" valueChangeListener="#{sub.setValue}"/>
	            <h:inputText value="#{sub.value}"  disabled="#{beanfield.disable}" converter="#{beanfield.converter}" required="#{beanfield.required}" size="35" rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator==null&&(sub.value!=''&&!sub.convertedValue||(sub.value==''&&!beanfield.multiValue))}" immediate="true" valueChangeListener="#{sub.setValue}"/>
	            <h:selectOneMenu value="#{sub.value}" style="max-width:23em" rendered="#{beanfield.fieldType=='selectOneMenu'&&(sub.value!=''&&!sub.convertedValue||(sub.value==''&&!beanfield.multiValue))}" >
                  <f:selectItems value="#{beanfield.displayItems}" />
             	</h:selectOneMenu>               	             	             	  
	        </t:div>
	        	       	        
	        <t:div rendered="#{sub.value==''&&beanfield.multiValue}" style="display:none;" styleClass="#{beanfield.name}hide" >    
	            <h:inputText value="#{sub.value}" size="35" rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator!=null&&sub.value==''&&beanfield.multiValue}" immediate="true" valueChangeListener="#{sub.setValue}"/>
	            <h:inputText value="#{sub.value}" size="35" rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator==null&&sub.value==''&&beanfield.multiValue}" immediate="true" valueChangeListener="#{sub.setValue}"/>
	            <h:selectOneMenu value="#{sub.value}" style="max-width:23em" rendered="#{beanfield.fieldType=='selectOneMenu'&&sub.value==''&&beanfield.multiValue}" >
                  <f:selectItems value="#{beanfield.displayItems}" />
             	</h:selectOneMenu> 	            
            </t:div>                                                                     
        </t:dataList>
                
        <t:div rendered="#{beanfield.fieldType=='selectManyCheckbox'}">             
             	<h:selectManyCheckbox value="#{beanfield.selectedItems}" rendered="#{beanfield.fieldType=='selectManyCheckbox'}" validator="#{beanfield.validator.validate}" layout="pageDirection">
                  <f:selectItems value="#{beanfield.displayItems}" />
             	</h:selectManyCheckbox>        
        </t:div>    
        <t:div rendered="#{beanfield.fieldType=='selectOneRadio'}">        
            	<h:selectOneRadio value="#{beanfield.value}" rendered="#{beanfield.fieldType=='selectOneRadio'}">
                  <f:selectItems value="#{beanfield.displayItems}" />
             	</h:selectOneRadio>              
        </t:div>   
        <h:outputText style="display:none" styleClass="#{beanfield.name}show constraint" value="#{msgs[beanfield.constraint]}" rendered="#{beanfield.constraint!=null}"/>                         
  		</h:column>  		
       	<h:column >
       		<h:graphicImage styleClass="helpTip" longdesc="#{msgs[beanfield.help]}" value="/media/images/help.jpg"  style="border: 0;" rendered="#{beanfield.help!=null&&!accountController.viewDataChange}"/>
       		<t:div style="display:none" styleClass="#{beanfield.name}modify">
       	  		<h:graphicImage styleClass="helpTip" longdesc="#{msgs[beanfield.notice]}" value="/media/images/redtriangular.jpg"  style="border: 0;" rendered="#{!beanfield.updateable&&!beanfield.disable&&!accountController.viewDataChange}"/>			        
          		<h:graphicImage alt="#{beanfield.name}" styleClass="show" value="/media/images/add.png"  style="border: 0;cursor:pointer" rendered="#{beanfield.multiValue&&!beanfield.disable&&(beanfield.fieldType=='inputText'||beanfield.fieldType=='selectOneMenu')}"/>
		  		<h:graphicImage alt="#{beanfield.name}" styleClass="hide" value="/media/images/remove.png"  style="border: 0;cursor:pointer" rendered="#{beanfield.multiValue&&!beanfield.disable&&(beanfield.fieldType=='inputText'||beanfield.fieldType=='selectOneMenu')}"/>
		  </t:div>	       	         	         	  
		</h:column>	
		<h:column >
			<t:htmlTag styleClass="#{beanfield.name}_modifyLink" rendered="#{!beanfield.disable&&beanfield.fieldType!='selectManyCheckbox'&&beanfield.fieldType!='selectOneRadio'}" value="a">			
  				<h:outputText value="#{msgs['DATACHANGE.MODIFICATION.LINK']}"/>  				
  			</t:htmlTag>
  		</h:column>  									
	  </h:dataTable>
	 </t:div>	 	 
    </t:dataList>      
  </div>

	<t:div style="margin-top:30;">
	  <e:commandButton id="next" value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushChangeInfoPerso}" />
	</t:div>
	
  </h:form>
	
	<h:form id="restart" style="display:none;"  >
	  <e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}" action="#{exceptionController.restart}" />
	</h:form>
	

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>