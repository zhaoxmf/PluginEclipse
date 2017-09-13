package com.yonyou.zhaoxmf.PluginEclipse.Shell;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.internal.ide.actions.BuildUtilities;
import org.eclipse.ui.internal.ide.dialogs.ResourceComparator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class ImportPatchWizardPage1 extends WizardPage {

	
	private CheckboxTableViewer projectNames;
	private Object[] selection;
	public ImportPatchWizardPage1() {
		super("选择NC项目");
		setTitle("选择NC项目");
		setDescription("选择需要导入NC补丁的项目");/*
		setImageDescriptor(ImageKeys.
				getImageDescriptor(ImageKeys.IMG_WIZARD_NEW));*/
	}

	@Override
	public void createControl(Composite parent) {
		createProjectSelectionTable(parent);
		setControl(parent);
	}
	private void createProjectSelectionTable(Composite radioGroup) {
	        projectNames = CheckboxTableViewer.newCheckList(radioGroup, SWT.BORDER);
	        projectNames.setContentProvider(new WorkbenchContentProvider());
	        projectNames.setLabelProvider(new WorkbenchLabelProvider());
	        projectNames.setComparator(new ResourceComparator(ResourceComparator.NAME));
	        projectNames.addFilter(new ViewerFilter() {
	            private final IProject[] projectHolder = new IProject[1];
	            public boolean select(Viewer viewer, Object parentElement, Object element) {
	                if (!(element instanceof IProject)) {
	                    return false;
	                }
	                IProject project = (IProject) element;
	                if (!project.isAccessible()) {
	                    return false;
	                }
	                projectHolder[0] = project;
	                return BuildUtilities.isEnabled(projectHolder, IncrementalProjectBuilder.CLEAN_BUILD);
	            }
	        });
	        projectNames.setInput(ResourcesPlugin.getWorkspace().getRoot());
	        GridData data = new GridData(GridData.FILL_BOTH);
	        data.horizontalSpan = 2;
	        data.widthHint = IDialogConstants.ENTRY_FIELD_WIDTH;
	        data.heightHint = IDialogConstants.ENTRY_FIELD_WIDTH;
	        projectNames.getTable().setLayoutData(data);
	       // projectNames.setCheckedElements(selection);
	        //table is disabled to start because all button is selected
	      //  projectNames.getTable().setEnabled(selectedButton.getSelection());
	        projectNames.addCheckStateListener(new ICheckStateListener() {
	            public void checkStateChanged(CheckStateChangedEvent event) {
	                selection = projectNames.getCheckedElements();
	                //updateEnablement();
	            }
	        });
	    }

}
