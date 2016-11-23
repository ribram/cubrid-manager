package com.cubrid.common.ui.common.dialog;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class MessageDialogWithScrollableMessage extends MessageDialog {

	public ArrayList<String> strings;
	
    public MessageDialogWithScrollableMessage(Shell parentShell, String title, String information, ArrayList<String> strings) {
    	super(parentShell, title, null, information,
                MessageDialog.INFORMATION, new String[] {  "OK" }, 0);
    	this.strings = strings;
    }

    @Override
    public Control createDialogArea(Composite parent) {
    	Composite content = (Composite) super.createDialogArea(parent);
    	content.setLayout(new GridLayout());
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.heightHint = 500;
        data.widthHint = 250;
        
    	
    	ScrolledComposite sc = new ScrolledComposite(content, SWT.H_SCROLL
                | SWT.V_SCROLL | SWT.BORDER);

        Composite composite = new Composite(sc, SWT.NONE);
        composite.setLayout(new FillLayout(SWT.VERTICAL));
        for(String s : strings){
            Label l = new Label(composite, SWT.NONE);
            l.setText(s);
        }
        
        sc.setLayoutData(data);
        sc.setContent(composite);
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);
        sc.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

        return parent; 
    }
}
