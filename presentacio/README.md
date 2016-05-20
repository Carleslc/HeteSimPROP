##Clases de la capa de presentació

###Utilització de ButtonColumn.java

La creadora de ButtonColumn és: ```public ButtonColumn(JTable table, Action action, int column)```
* table serà la JTable on es vol dibuixar el JButton,
* action és una implementació de Action que es vol que s'executi al clicar el botó,
* column és la columna on es dibuixarà el botó

Per utilitzar ButtonColumn s'ha de fer el següent:
```
Action action = new AbstractAction() {
  public void actionPerformed(ActionEvent e) {
		       /*Acció a realitzar*/
	}
};
		 
ButtonColumn buttonColumn = new ButtonColumn(table, action, columna);
buttonColumn.setMnemonic(KeyEvent.VK_D);
```
