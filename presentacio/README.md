##Clases de la capa de presentaci\u00F3

###Utilitzaci\u00F3 de ButtonColumn.java

La creadora de ButtonColumn és: ```public ButtonColumn(JTable table, Action action, int column)```
* table ser\u00E0 la JTable on es vol dibuixar el JButton,
* action és una implementaci\u00F3 de Action que es vol que s'executi al clicar el bot\u00F3,
* column és la columna on es dibuixar\u00E0 el bot\u00F3

Per utilitzar ButtonColumn s'ha de fer el següent:
```
Action action = new AbstractAction() {
  public void actionPerformed(ActionEvent e) {
		       /*Acci\u00F3 a realitzar*/
	}
};
		 
ButtonColumn buttonColumn = new ButtonColumn(table, action, columna);
buttonColumn.setMnemonic(KeyEvent.VK_D);
```
