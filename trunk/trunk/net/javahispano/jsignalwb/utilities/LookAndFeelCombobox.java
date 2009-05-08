package net.javahispano.jsignalwb.utilities;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Esta clase es la que realmente cambia el Look and Feel del cuadro de diálogo en el cual
 * está incluida y del JFrame que se le pasa en el constructor.
 * Extiende a  JCombobox e implementa la interface Runnable, ya que la acción de
 * cambiar el Look and Feel la haremos desde otro Thread.
 * @author Abraham Otero
 * @version 1.0
 */
class LookAndFeelCombobox extends JComboBox implements ActionListener, Runnable
{
   private String look_and_feel;
   private String look_and_feel_no_soportado, otro_error;

    public LookAndFeelCombobox(JFrame frame,String look_and_feel_no_soportado,String otro_error){
        super();
        this.frame = frame;
        this.look_and_feel_no_soportado = look_and_feel_no_soportado;
        this.otro_error = otro_error;
        actualizaLookAnfFeel();
        reflejaElLookAndFeelActual();
        addActionListener(this);
    }

    /**
     * Añade al combobox los Looks an feel instalados.
     */
    protected void actualizaLookAnfFeel(){
        UIManager.LookAndFeelInfo[] look_and_feel_info = UIManager.getInstalledLookAndFeels();
        for (int i = 0;i < look_and_feel_info.length;i++)
            addItem(look_and_feel_info[i].getName());
    }

    /**
     * Seleciona en el combobox el Look and Feel actual de la plataforma.
     */
    protected void reflejaElLookAndFeelActual()
    {
        String nombre_del_Look_and_Feel_actual = UIManager.getLookAndFeel().getName();

        UIManager.LookAndFeelInfo[] lnfInfos = UIManager.getInstalledLookAndFeels();
        for (int i = 0;i < lnfInfos.length;i++)
        {
            if (lnfInfos[i].getName().equals(nombre_del_Look_and_Feel_actual))
            {
                setSelectedIndex(i);
                break;
            }
        }
    }

    /**
     * Este es el método que hace todo el trabajo.
     * @param actionEvent
     */
    public void actionPerformed(ActionEvent actionEvent)
    {
      //Miramos que índice se ha selcccionado
        int look_and_feel_selecionado = getSelectedIndex();
        UIManager.LookAndFeelInfo[] look_and_feels_informacion = UIManager.getInstalledLookAndFeels();
        //Este es el look and feel selecionado
        String nuevo_look_and_feel =  look_and_feels_informacion[look_and_feel_selecionado].getClassName();
        //y este es su nombre
        String nuevo_look_and_feel_nombre = look_and_feels_informacion[look_and_feel_selecionado].getName();
        //Intento poner el Look and Feel selcionado
        try {
            UIManager.setLookAndFeel(look_and_feels_informacion[look_and_feel_selecionado].getClassName());
	    this.look_and_feel = nuevo_look_and_feel;
        }
        //Si se lanza una UnsupportedLookAndFeelException muestro el mensaje en el que se indica
        //que es elook and feel no está soportado en esta platafomra
        catch(UnsupportedLookAndFeelException e)
        {
           erroralActualizarLookAndFeel(e, look_and_feel_no_soportado);
        }
        //Si es una excepción d eotro tipo muestro el error genérico.
        catch(Exception e){
           erroralActualizarLookAndFeel(e, otro_error);
        }
        //Invoco el metodo run de este thread, pero con SwingUtilities..invokeLater(),
        //de este modo permito que todos los evetos pendientes sean procesados antes de
        //ejeutar este thread. Esto es necesario ya que las Swing no están sincronizadas
        SwingUtilities.invokeLater(this);
    }

    /**
     * Metod que invocamos si algo sale mal.
     * @param e Excepcion que se produce
     * @param mensaje Mensaje a mostrar en el cuadro de diálogo.
     */
    protected void erroralActualizarLookAndFeel(Exception e, String mensaje){
      JOptionPane.showConfirmDialog(this,mensaje,"Advertencia!!!",JOptionPane.DEFAULT_OPTION,
          JOptionPane.WARNING_MESSAGE);
      e.printStackTrace();
      reflejaElLookAndFeelActual();
    }

    /** Determiana la raiz del contenedor al que este componente pertenece,
    una vez determianda actualizad su Look and Feel.
    */
    public void run()
    {
        //Encontrar el panel raiz de la ventana en la 2que está este combobox
        JRootPane raiz_del_esta_ventana = SwingUtilities.getRootPane(this);
        if (raiz_del_esta_ventana != null)
        {
          //Este bucle se ejecutará hasta que se ejecute uno de los breack internos
            while (true)
            {
              //Buscamos el padre del JRootPane
                Container nueva_raiz = raiz_del_esta_ventana.getParent();
                //si es null el es el contenedor padre de todos
                if (nueva_raiz == null)
                    break;
                //Hallamos el RootPane del contenedor. Si es  null o es igual al
                //al propio JRootPane acabsmos la búsqueda
                JRootPane possibleRoot = SwingUtilities.getRootPane(nueva_raiz);
                if (possibleRoot == null || possibleRoot == raiz_del_esta_ventana)
                    break;
                //Si no seguimos con el JRootPane nuevo
                raiz_del_esta_ventana = possibleRoot;
            }
            //Actualizamos la apariencia de este componete y todos los que estén añadidos sobre el.
            SwingUtilities.updateComponentTreeUI(raiz_del_esta_ventana);
        }
        //Cambia el look and feel de la ventana que le pasamos a LookAndFeel
        //El proceso es idéntico al del caso anterior.
        raiz_del_esta_ventana = SwingUtilities.getRootPane(frame);
        if (raiz_del_esta_ventana != null)
        {
            while (true)
            {
                Container rootParent = raiz_del_esta_ventana.getParent();
                if (rootParent == null)
                    break;

                JRootPane possibleRoot = SwingUtilities.getRootPane(rootParent);
                if (possibleRoot == null || possibleRoot == raiz_del_esta_ventana)
                    break;

                raiz_del_esta_ventana = possibleRoot;
            }

            SwingUtilities.updateComponentTreeUI(raiz_del_esta_ventana);
        }
    }

    /**
     * Devuelve el Look And Feel Selecionado.
     * @return Nombre del Look and Feel selecionado.
     */
   String getLookAnfFeel(){
        return look_and_feel;
   }
  /**
   * Indica que texto se debe mostar  mostrar cuando se produce una excepción cualquiera
   * salvo aquella producida por un Look and Feel no soportado.
   * @param _otro_error Texto a mostrar cuando se produce una excepción cualquiera.
   */
    public void setOtroError(String _otro_error) {
    otro_error = _otro_error;
  }
  /**
   * Indica que texto se debe mostrar cuando el usuario selecione un Look and Feel no soportado.
   * @param _look_and_feel_no_soportado Texto que se debe mostrar cuando el usuario elige un Look and Feel no soportado.
   */
  public void setLookAndFeelNoSoportado(String _look_and_feel_no_soportado) {
    look_and_feel_no_soportado = _look_and_feel_no_soportado;
  }

    private JFrame frame;
}
