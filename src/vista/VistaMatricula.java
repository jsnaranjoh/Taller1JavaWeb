/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.event.ItemEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelo.Estudiante;
import modelo.Materia;
import modelo.Matricula;
import modelo.MatriculaPK;
import persistencia.EstudianteJpaController;
import persistencia.MateriaJpaController;
import persistencia.MatriculaJpaController;

/**
 *
 * @author jsnar
 */
public class VistaMatricula extends javax.swing.JPanel {

    private List<Materia> listaMateria;
    private List<Estudiante> listaEstudiante;
    private Matricula matricula;
    /**
     * Creates new form VistaMatricula
     */
    public VistaMatricula() {
        matricula = new Matricula();
        /**
         * Obtiene la lista de nombres de materias y se agregan a un modelo de comboBox
         * dicho modelo es cargado en jComboBox2 para que el usuario seleccione una opción
         */
        DefaultComboBoxModel materiaComboBoxModel = new DefaultComboBoxModel();
        MateriaJpaController controladorMateria = new MateriaJpaController();
        listaMateria = controladorMateria.findMateriaEntities();
        for (Materia materia : listaMateria) {
            Object nombreMateria = materia.getNombremateria();
            materiaComboBoxModel.addElement(nombreMateria);
        }
        
        DefaultComboBoxModel estudianteComboBoxModel = new DefaultComboBoxModel();
        EstudianteJpaController controladorEstudiante = new EstudianteJpaController();
        listaEstudiante = controladorEstudiante.findEstudianteEntities();
        for (Estudiante estudiante : listaEstudiante) {
            Object nombreEstudiante = estudiante.getNombreestudiante() + " " + estudiante.getApellidoestudiante();
            estudianteComboBoxModel.addElement(nombreEstudiante);
        }
        initComponents();
        jComboBox2.setModel(materiaComboBoxModel);
        jComboBox3.setModel(estudianteComboBoxModel);

        int numeroMateriaSeleccionada = listaMateria.get(0).getNumeromateria();
        jTextField5.setText(String.valueOf(numeroMateriaSeleccionada));
        
        Long numeroEstudianteSeleccionado = listaEstudiante.get(0).getDocumentoestudiante();
        jTextField2.setText(String.valueOf(numeroEstudianteSeleccionado));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();

        jLabel2.setText("Nombre Estudiante");

        jLabel3.setText("No. Documento Estudiante");

        jTextField2.setEnabled(false);

        jLabel4.setText("Materia");

        jLabel5.setText("Nota");

        jLabel6.setText("Estado");

        jButton1.setText("Crear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Leer");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Actualizar");

        jButton4.setText("Eliminar ");

        jLabel7.setText("No. Materia");

        jTextField5.setEnabled(false);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "APROBADO", "DESAPROBADO" }));

        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton2, jButton3, jButton4});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2, jButton3, jButton4});

    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        /**
         * Cuando se seleeciona una opcion en el comboBox
         * lee el indice de la materia seleccionada, el indice es el mismo
         * que en la lista de materias, por lo tanto dicho indice sirve para consular el numero de materia
         * en la lista de materias y lo agrega a jTextField5
         */
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            int indexMateriaSeleccionada = jComboBox2.getSelectedIndex();
            int numeroMateriaSeleccionada = listaMateria.get(indexMateriaSeleccionada).getNumeromateria();
            jTextField5.setText(String.valueOf(numeroMateriaSeleccionada));
        }
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            int indexEstudianteSeleccionado = jComboBox3.getSelectedIndex();
            Long numeroEstudianteSeleccionado = listaEstudiante.get(indexEstudianteSeleccionado).getDocumentoestudiante();
            jTextField2.setText(String.valueOf(numeroEstudianteSeleccionado));
        }
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            Long documentoEstudiante = Long.parseLong(jTextField2.getText());
            Integer numeroMateria = Integer.parseInt(jTextField5.getText());
            Double nota = Double.parseDouble(jTextField4.getText());
            String estado = jComboBox1.getSelectedItem()+"";
            
            Estudiante estudiante;
            EstudianteJpaController controladorEstudiante = new EstudianteJpaController();
            estudiante = controladorEstudiante.findEstudiante(documentoEstudiante);
            
            Materia materia;
            MateriaJpaController controladorMateria = new MateriaJpaController();
            materia = controladorMateria.findMateria(numeroMateria);
            
            matricula.setEstudiante(estudiante);
            matricula.setMateria(materia);
            matricula.setNota(nota);
            matricula.setEstado(estado);
            
            MatriculaJpaController controladorMatricula= new MatriculaJpaController();
            controladorMatricula.create(matricula);
            JOptionPane.showMessageDialog(this, "Matricula realizada exitosamente");
            
            jTextField4.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            Long documentoEstudiante = Long.parseLong(jTextField2.getText());
            Integer numeroMateria = Integer.parseInt(jTextField5.getText());
            
            MatriculaJpaController controladorMatricula = new MatriculaJpaController();
            MatriculaPK llavePrimariaMatricula = new MatriculaPK(documentoEstudiante, numeroMateria);
            matricula = controladorMatricula.findMatricula(llavePrimariaMatricula);
            
            jTextField4.setText(String.valueOf(matricula.getNota()));
            if (matricula.getEstado().equals("APROBADO")) {
                jComboBox1.setSelectedIndex(0);
            } else {
                jComboBox1.setSelectedIndex(1);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "El estudiante no ha matriculado esta materia.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
