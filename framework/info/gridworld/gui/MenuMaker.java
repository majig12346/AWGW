/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2005-2006 Cay S. Horstmann (http://horstmann.com)
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * @author Cay Horstmann
 */

package info.gridworld.gui;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import majig12346.terrain.Terrain;
import majig12346.terrain.properties.Factory;
import majig12346.terrain.properties.Property;
import majig12346.units.Carry;
import majig12346.units.Unit;
import majig12346.units.land.Infantry;
import majig12346.weapons.WeaponType;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.beans.PropertyEditorSupport;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Makes the menus for constructing new occupants and grids, and for invoking
 * methods on existing occupants. <br />
 * This code is not tested on the AP CS A and AB exams. It contains GUI
 * implementation details that are not intended to be understood by AP CS
 * students.
 */
public class MenuMaker<T>
{
    /**
     * Constructs a menu maker for a given world.
     * @param parent the frame in which the world is displayed
     * @param resources the resource bundle
     * @param displayMap the display map
     */
    public MenuMaker(WorldFrame<T> parent, ResourceBundle resources,
            DisplayMap displayMap)
    {
        this.parent = parent;
        this.resources = resources;
        this.displayMap = displayMap;
    }

    
    
    
    
    
    
    
    
    
    /**
     * Makes a menu that displays all available actions that a Unit may perform when moving to this tile
     * @param occupant the unit whose available actions should be displayed
     * @param loc the location that the Unit would move to
     * @return the menu to pop up
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     */
    public JPopupMenu makeMoveMenu(T occupant, Location loc) throws NoSuchMethodException, SecurityException
    {
        this.occupant = occupant;
        System.out.println("line 106 Menu: currentLocation set to loc\nloc is "+loc.getClass().getName());
        this.currentLocation = loc;
        JPopupMenu menu = new JPopupMenu();
        Method[] methods = getValidMethods();
        System.out.println("methods is "+methods.length+" size.");
        //Method[] methods = getMethods();
//        Class oldDcl = null;
        for (int i = 0; i < methods.length; i++)
        {
//            Class dcl = methods[i].getDeclaringClass();
//            if (dcl != Object.class)
//            {
//                if (i > 0 && dcl != oldDcl)
//                    menu.addSeparator();
                menu.add(new MethodItem(methods[i]));
                System.out.println("added method, see line 118 of MenuMaker");
//            }
//            oldDcl = dcl;
        }
        return menu;
    }
    
    
    
    
    private Method[] getValidMethods() throws NoSuchMethodException, SecurityException
    {
    	Unit u = null;
    	try{
    		u = (Unit)occupant;
    	}catch(ClassCastException e){
    		JOptionPane.showMessageDialog(null, "not sure how you called this "
    				+ "method\nsee MenuMaker's getValidMethods()");
    		System.exit(-1);
    	}
        Class cl = occupant.getClass();
        ArrayList<Method> ans = new ArrayList<Method>();
        //every unit has the move method if it can Move
        if(u.canMove()){
        	System.out.println("can move, added!");
        	ans.add(Unit.class.getMethod("move", Terrain.class));
        }else{
        	System.out.println("cant move");
        }
        //units can fire on enemies if not unarmed and in range
        System.out.println("checking weps");
        if(u.getWeapons()[0].getWeaponType()!=WeaponType.NONE||null!=u.getWeapons()[1]){
        	if(u.canTarget((Unit) u.getGrid().get(currentLocation))){
        		
        		Action a = new Action(){
        			public boolean enabled = true;
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void addPropertyChangeListener(
							PropertyChangeListener listener) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public Object getValue(String key) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public boolean isEnabled() {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public void putValue(String key, Object value) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void removePropertyChangeListener(
							PropertyChangeListener listener) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void setEnabled(boolean b) {
						enabled = b;
					}
        			
        		};
        		JMenuItem tmp = new JMenuItem();
        		
        		
        		
        		ans.add(Unit.class.getMethod("fire", Unit.class));
        	}
        }
        System.out.println("checking carriability");
        //if unit can be carried
        if(u.getGrid().get(currentLocation)instanceof Carry){
        	Carry c = (Carry) u.getGrid().get(currentLocation);
        	if(c.canCarry(u)){
        		ans.add(Unit.class.getMethod("load", Carry.class));
        	}
        }
        //if unit is carry, can drop something off
        DROPOFF:
	        if(u instanceof Carry){
	        	Carry c = (Carry) u;
	        	if(!(c.getUnits().isEmpty())){
	        		for(Unit carried:c.getUnits()){
	        			Terrain t = (Terrain) currentLocation;
	        			if(t.getMoveCost(carried.getMovementType())!=999){
	        				ans.add(Carry.class.getMethod("drop"));
	        				break DROPOFF;
	        			}
	        		}
	        	}
	        }
        //TODO stealth functions
        
        //infantry can capture
        if(u instanceof Infantry){
        	if(currentLocation instanceof Property && 
        			((Property) currentLocation).getOwner()!=u.getOwner()){
        		ans.add(Infantry.class.getMethod("capture"));
        	}
        }
        //wow do I really need to do this
        Method[] realAns = new Method[ans.size()];
        for(int i=0;i<realAns.length;i++){
        	realAns[i] = ans.get(i);
        }
        return realAns;
        
        
        //return (Method[]) (ans.toArray());
    }
        
        
        
//        Arrays.sort(methods, new Comparator<Method>()
//        {
//            public int compare(Method m1, Method m2)
//            {
//                int d1 = depth(m1.getDeclaringClass());
//                int d2 = depth(m2.getDeclaringClass());
//                if (d1 != d2)
//                    return d2 - d1;
//                int d = m1.getName().compareTo(m2.getName());
//                if (d != 0)
//                    return d;
//                d1 = m1.getParameterTypes().length;
//                d2 = m2.getParameterTypes().length;
//                return d1 - d2;
//            }
//
//            private int depth(Class cl)
//            {
//                if (cl == null)
//                    return 0;
//                else
//                    return 1 + depth(cl.getSuperclass());
//            }
//        });
//        return methods;
//}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Makes a menu that displays all public methods of an object
     * @param occupant the object whose methods should be displayed
     * @param loc the location of the occupant
     * @return the menu to pop up
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     */
    public JPopupMenu makeMethodMenu(T occupant, Location loc) throws NoSuchMethodException, SecurityException
    {
    	
    	return makeMoveMenu(occupant, loc);
    	//old code
    	
//        this.occupant = occupant;
//        this.currentLocation = loc;
//        JPopupMenu menu = new JPopupMenu();
//        Method[] methods = getMethods();
//        Class oldDcl = null;
//        for (int i = 0; i < methods.length; i++)
//        {
//            Class dcl = methods[i].getDeclaringClass();
//            if (dcl != Object.class)
//            {
//                if (i > 0 && dcl != oldDcl)
//                    menu.addSeparator();
//                menu.add(new MethodItem(methods[i]));
//            }
//            oldDcl = dcl;
//        }
//        return menu;
        
        
        
    }

    /**
     * Makes a menu that displays all public constructors of a collection of
     * classes.
     * @param classes the classes whose constructors should be displayed
     * @param loc the location of the occupant to be constructed
     * @return the menu to pop up
     */
    public JPopupMenu makeConstructorMenu(Collection<Class> classes,
            Location loc)
    {
    	System.out.println("making constructor menu. See line 278 of MenuMaker");
        this.currentLocation = loc;
        JPopupMenu menu = new JPopupMenu();
        //???
        //boolean first = true;
        
        //
        if(!(loc instanceof Factory)){
    		return menu;
    	}
        try{
        	Factory fac = (Factory) loc;
        	for(Constructor<? extends Unit> constructor: fac.getBuildableUnits()){
        		JMenuItem tmp = new JMenuItem();
        		Action a = new Action() {
        			public boolean enabled = true;
        			@Override
					public void actionPerformed(ActionEvent e) {
						fac.buildUnit(constructor);
					}
					@Override
					public void setEnabled(boolean b) {
						enabled = b;
					}
					@Override
					public void removePropertyChangeListener(PropertyChangeListener listener) {	
					}
					@Override
					public void putValue(String key, Object value) {	
					}
					@Override
					public boolean isEnabled() {
						return enabled;
					}
					
					@Override
					public Object getValue(String key) {
						return null;
					}
					
					@Override
					public void addPropertyChangeListener(PropertyChangeListener listener) {
					}
				};
				a.setEnabled(true);
        		tmp.setAction(a);
        		tmp.setText(constructor.getName());
        		//tmp.setIcon();
        		menu.add(tmp);
        	}
        	
        }catch(Exception e){
        	e.printStackTrace();
        }
        return menu;
        
        
        
        
        
        //
//        Iterator<Class> iter = classes.iterator();
//        while (iter.hasNext())
//        {
//            if (first)
//                first = false;
//            else
//                menu.addSeparator();
//            Class cl = iter.next();
//            Constructor[] cons = (Constructor[]) cl.getConstructors();
//            for (int i = 0; i < cons.length; i++)
//            {
//                menu.add(new OccupantConstructorItem(cons[i]));
//            }
//        }
        
        
        
    }

    /**
     * Adds menu items that call all public constructors of a collection of
     * classes to a menu
     * @param menu the menu to which the items should be added
     * @param classes the collection of classes
     */
    public void addConstructors(JMenu menu, Collection<Class> classes)
    {
        boolean first = true;
        Iterator<Class> iter = classes.iterator();
        while (iter.hasNext())
        {
            if (first)
                first = false;
            else
                menu.addSeparator();
            Class cl = iter.next();
            Constructor[] cons = cl.getConstructors();
            for (int i = 0; i < cons.length; i++)
            {
                menu.add(new GridConstructorItem(cons[i]));
            }
        }
    }

    private Method[] getMethods()
    {
        Class cl = occupant.getClass();
        //TODO ???
        Method[] methods = cl.getMethods();
     //   Method[] methods = new Method[];
        
        Arrays.sort(methods, new Comparator<Method>()
        {
            public int compare(Method m1, Method m2)
            {
                int d1 = depth(m1.getDeclaringClass());
                int d2 = depth(m2.getDeclaringClass());
                if (d1 != d2)
                    return d2 - d1;
                int d = m1.getName().compareTo(m2.getName());
                if (d != 0)
                    return d;
                d1 = m1.getParameterTypes().length;
                d2 = m2.getParameterTypes().length;
                return d1 - d2;
            }

            private int depth(Class cl)
            {
                if (cl == null)
                    return 0;
                else
                    return 1 + depth(cl.getSuperclass());
            }
        });
        return methods;
    }

    /**
     * A menu item that shows a method or constructor.
     */
    private class MCItem extends JMenuItem
    {
        public String getDisplayString(Class retType, String name,
                Class[] paramTypes)
        {
            StringBuffer b = new StringBuffer();
            b.append("<html>");
            if (retType != null)
                appendTypeName(b, retType.getName());
            b.append(" <font color='blue'>");
            appendTypeName(b, name);
            b.append("</font>( ");
            for (int i = 0; i < paramTypes.length; i++)
            {
                if (i > 0)
                    b.append(", ");
                appendTypeName(b, paramTypes[i].getName());
            }
            b.append(" )</html>");
            return b.toString();
        }

        public void appendTypeName(StringBuffer b, String name)
        {
            int i = name.lastIndexOf('.');
            if (i >= 0)
            {
                String prefix = name.substring(0, i + 1);
                if (!prefix.equals("java.lang"))
                {
                    b.append("<font color='gray'>");
                    b.append(prefix);
                    b.append("</font>");
                }
                b.append(name.substring(i + 1));
            }
            else
                b.append(name);
        }

        public Object makeDefaultValue(Class type)
        {
            if (type == int.class)
                return new Integer(0);
            else if (type == boolean.class)
                return Boolean.FALSE;
            else if (type == double.class)
                return new Double(0);
            else if (type == String.class)
                return "";
            else if (type == Color.class)
                return Color.BLACK;
            else if (type == Location.class)
                return currentLocation;
            else if (Grid.class.isAssignableFrom(type))
                return currentGrid;
            else
            {
                try
                {
                    return type.newInstance();
                }
                catch (Exception ex)
                {
                    return null;
                }
            }
        }
    }

    private abstract class ConstructorItem extends MCItem
    {
        public ConstructorItem(Constructor c)
        {
            setText(getDisplayString(null, c.getDeclaringClass().getName(), c
                    .getParameterTypes()));
            this.c = c;
        }

        public Object invokeConstructor()
        {
            Class[] types = c.getParameterTypes();
            Object[] values = new Object[types.length];

            for (int i = 0; i < types.length; i++)
            {
                values[i] = makeDefaultValue(types[i]);
            }

            if (types.length > 0)
            {
                PropertySheet sheet = new PropertySheet(types, values);
                JOptionPane.showMessageDialog(this, sheet, resources
                        .getString("dialog.method.params"),
                        JOptionPane.QUESTION_MESSAGE);
                values = sheet.getValues();
            }

            try
            {
                return c.newInstance(values);
            }
            catch (InvocationTargetException ex)
            {
                parent.new GUIExceptionHandler().handle(ex.getCause());
                return null;
            }
            catch (Exception ex)
            {
                parent.new GUIExceptionHandler().handle(ex);
                return null;
            }
        }

        private Constructor c;
    }

    private class OccupantConstructorItem extends ConstructorItem implements
            ActionListener
    {
        public OccupantConstructorItem(Constructor c)
        {
            super(c);
            addActionListener(this);
            setIcon(displayMap.getIcon(c.getDeclaringClass(), 16, 16));
        }

        @SuppressWarnings("unchecked")
        public void actionPerformed(ActionEvent event)
        {
            T result = (T) invokeConstructor();
            parent.getWorld().add(currentLocation, result);
            parent.repaint();
        }
    }

    private class GridConstructorItem extends ConstructorItem implements
            ActionListener
    {
        public GridConstructorItem(Constructor c)
        {
            super(c);
            addActionListener(this);
            setIcon(displayMap.getIcon(c.getDeclaringClass(), 16, 16));
        }

        @SuppressWarnings("unchecked")
        public void actionPerformed(ActionEvent event)
        {
            Grid<T> newGrid = (Grid<T>) invokeConstructor(); 
            parent.setGrid(newGrid);
        }
    }

    private class MethodItem extends MCItem implements ActionListener
    {
        public MethodItem(Method m)
        {
            setText(getDisplayString(m.getReturnType(), m.getName(), m
                    .getParameterTypes()));
            this.m = m;
            addActionListener(this);
            setIcon(displayMap.getIcon(m.getDeclaringClass(), 16, 16));
        }

        public void actionPerformed(ActionEvent event)
        {
            Class[] types = m.getParameterTypes();
            Object[] values = new Object[types.length];

            for (int i = 0; i < types.length; i++)
            {
                values[i] = makeDefaultValue(types[i]);
            }

            if (types.length > 0)
            {
                PropertySheet sheet = new PropertySheet(types, values);
                JOptionPane.showMessageDialog(this, sheet, resources
                        .getString("dialog.method.params"),
                        JOptionPane.QUESTION_MESSAGE);
                values = sheet.getValues();
            }

            try
            {
                Object result = m.invoke(occupant, values);
                parent.repaint();
                if (m.getReturnType() != void.class)
                {
                    String resultString = result.toString();
                    Object resultObject;
                    final int MAX_LENGTH = 50;
                    final int MAX_HEIGHT = 10;
                    if (resultString.length() < MAX_LENGTH)
                        resultObject = resultString;
                    else
                    {
                        int rows = Math.min(MAX_HEIGHT, 1
                                + resultString.length() / MAX_LENGTH);
                        JTextArea pane = new JTextArea(rows, MAX_LENGTH);
                        pane.setText(resultString);
                        pane.setLineWrap(true);
                        resultObject = new JScrollPane(pane);
                    }
                    JOptionPane.showMessageDialog(parent, resultObject,
                            resources.getString("dialog.method.return"),
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            catch (InvocationTargetException ex)
            {
                parent.new GUIExceptionHandler().handle(ex.getCause());
            }
            catch (Exception ex)
            {
                parent.new GUIExceptionHandler().handle(ex);
            }
        }

        private Method m;
    }

    private T occupant;
    private Grid currentGrid;
    private Location currentLocation;
    private WorldFrame<T> parent;
    private DisplayMap displayMap;
    private ResourceBundle resources;
}

class PropertySheet extends JPanel
{
    /**
     * Constructs a property sheet that shows the editable properties of a given
     * object.
     * @param object the object whose properties are being edited
     */
    public PropertySheet(Class[] types, Object[] values)
    {
        this.values = values;
        editors = new PropertyEditor[types.length];
        setLayout(new FormLayout());
        for (int i = 0; i < values.length; i++)
        {
            JLabel label = new JLabel(types[i].getName());
            add(label);
            if (Grid.class.isAssignableFrom(types[i]))
            {
                label.setEnabled(false);
                add(new JPanel());
            }
            else
            {
                editors[i] = getEditor(types[i]);
                if (editors[i] != null)
                {
                    editors[i].setValue(values[i]);
                    add(getEditorComponent(editors[i]));
                }
                else
                    add(new JLabel("?"));
            }
        }
    }

    /**
     * Gets the property editor for a given property, and wires it so that it
     * updates the given object.
     * @param bean the object whose properties are being edited
     * @param descriptor the descriptor of the property to be edited
     * @return a property editor that edits the property with the given
     * descriptor and updates the given object
     */
    public PropertyEditor getEditor(Class type)
    {
        PropertyEditor editor;
        editor = defaultEditors.get(type);
        if (editor != null)
            return editor;
        editor = PropertyEditorManager.findEditor(type);
        return editor;
    }

    /**
     * Wraps a property editor into a component.
     * @param editor the editor to wrap
     * @return a button (if there is a custom editor), combo box (if the editor
     * has tags), or text field (otherwise)
     */
    public Component getEditorComponent(final PropertyEditor editor)
    {
        String[] tags = editor.getTags();
        String text = editor.getAsText();
        if (editor.supportsCustomEditor())
        {
            return editor.getCustomEditor();
        }
        else if (tags != null)
        {
            // make a combo box that shows all tags
            final JComboBox comboBox = new JComboBox(tags);
            comboBox.setSelectedItem(text);
            comboBox.addItemListener(new ItemListener()
            {
                public void itemStateChanged(ItemEvent event)
                {
                    if (event.getStateChange() == ItemEvent.SELECTED)
                        editor.setAsText((String) comboBox.getSelectedItem());
                }
            });
            return comboBox;
        }
        else
        {
            final JTextField textField = new JTextField(text, 10);
            textField.getDocument().addDocumentListener(new DocumentListener()
            {
                public void insertUpdate(DocumentEvent e)
                {
                    try
                    {
                        editor.setAsText(textField.getText());
                    }
                    catch (IllegalArgumentException exception)
                    {
                    }
                }

                public void removeUpdate(DocumentEvent e)
                {
                    try
                    {
                        editor.setAsText(textField.getText());
                    }
                    catch (IllegalArgumentException exception)
                    {
                    }
                }

                public void changedUpdate(DocumentEvent e)
                {
                }
            });
            return textField;
        }
    }

    public Object[] getValues()
    {
        for (int i = 0; i < editors.length; i++)
            if (editors[i] != null)
                values[i] = editors[i].getValue();
        return values;
    }

    private PropertyEditor[] editors;
    private Object[] values;

    private static Map<Class, PropertyEditor> defaultEditors;

    // workaround for Web Start bug
    public static class StringEditor extends PropertyEditorSupport
    {
        public String getAsText()
        {
            return (String) getValue();
        }

        public void setAsText(String s)
        {
            setValue(s);
        }
    }

    static
    {
        defaultEditors = new HashMap<Class, PropertyEditor>();
        defaultEditors.put(String.class, new StringEditor());
        defaultEditors.put(Location.class, new LocationEditor());
        defaultEditors.put(Color.class, new ColorEditor());
    }
}
