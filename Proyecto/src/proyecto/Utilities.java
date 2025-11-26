package proyecto;

import javax.swing.JOptionPane;

public class Utilities {

    private Estados[] estados = Estados.values();

    public Estados seleccionarEstado() {
        int opcion = JOptionPane.showOptionDialog(null,
                "Seleccione el estado:",
                "Estado",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                estados,
                estados[0]
        );
        if (opcion >= 0 && opcion < estados.length) {
            return estados[opcion];
        }
        return Estados.Activo;
    }

}
