/******************************************************************************* 
 * Copyright (c) 2013 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.tools.jst.web.ui.palette.html.jquery.wizard;

import java.beans.PropertyChangeEvent;

import org.eclipse.swt.widgets.Composite;
import org.jboss.tools.common.model.ui.editors.dnd.ValidationException;
import org.jboss.tools.common.ui.widget.editor.IFieldEditor;
import org.jboss.tools.jst.web.kb.internal.taglib.html.jq.JQueryMobileVersion;
import org.jboss.tools.jst.web.ui.internal.properties.advanced.LayoutUtil;
import org.jboss.tools.jst.web.ui.internal.properties.advanced.LayoutUtil.TwoColumns;
import org.jboss.tools.jst.web.ui.palette.html.wizard.AbstractNewHTMLWidgetWizardPage;
import org.jboss.tools.jst.web.ui.palette.html.wizard.WizardMessages;

/**
 * 
 * @author Viacheslav Kabanovich
 *
 */
public class NewJQueryWidgetWizardPage extends AbstractNewHTMLWidgetWizardPage implements JQueryConstants {
	IFieldEditor addID = null;
	IFieldEditor id = null;

	public NewJQueryWidgetWizardPage(String pageName, String title) {
		super(pageName, title);
	}

	@Override
    public NewJQueryWidgetWizard<?> getWizard() {
        return (NewJQueryWidgetWizard<?>)super.getWizard();
    }

	protected JQueryMobileVersion getVersion() {
		return getWizard().getVersion();
	}

	protected void createIDEditor(Composite parent, boolean enabling) {
		if(!enabling) {
			id = JQueryFieldEditorFactory.createIDEditor();
			addEditor(id, parent);
		} else {
			addID = JQueryFieldEditorFactory.createAddIDEditor();
			addEditor(addID, parent);
			id = JQueryFieldEditorFactory.createIDEditor2();
			addEditor(id, parent);
			updateIDEnablement();
		}
	}

	protected boolean isTrue(String editorID) {
		return TRUE.equals(getEditorValue(editorID));
	}

	public boolean isIDEnabled() {
		return id != null && (addID == null || TRUE.equals(addID.getValueAsString()));
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if(EDITOR_ID_ADD_ID.equals(evt.getPropertyName())) {
			updateIDEnablement();
		}
		super.propertyChange(evt);
	}

	public void validate() throws ValidationException {
		if(isIDEnabled()) {
			String id = getEditorValue(EDITOR_ID_ID);
			if(id != null && !getWizard().isIDAvailable(id)) {
				throw new ValidationException(WizardMessages.errorIDisUsed);
			}
		}
	}

	protected void updateIDEnablement() {
		if(addID != null && id != null) {
			id.setEnabled(isIDEnabled());
		}
	}

	protected void setEnabled(String editorName, boolean value) {
		IFieldEditor editor = getEditor(editorName);
		if(editor != null) {
			editor.setEnabled(value);
		}
	}

	public static Composite[] createColumns(Composite parent, int k) {
		return LayoutUtil.createColumns(parent, k);
	}

	public static TwoColumns createTwoColumns(Composite parent) {
		return LayoutUtil.createTwoColumns(parent);
	}

}
