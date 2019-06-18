package views;

import controllers.UserController;
import database.DBConnection;
import database.migrations.MigrationBuilder;
import database.seeds.SeederBuilder;
import models.Paginate;
import models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class App extends JFrame {

    private static final int WINDOW_WIDTH = 510;
    private static final int WINDOW_HEIGHT = 500;
    private static final int PER_PAGE = 12;
    private JPanel panelBody;

    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel defaultTableModel;

    UserController userController = new UserController();
    private Paginate<User> paginateUsers;

    public static void main(String[] args) {
        DBConnection.getConnection();

        if (DBConnection.hasBeenRecreated) {
            MigrationBuilder.migrate();
            SeederBuilder.seed();
        }
        new App();
    }

    App() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        init();

        setVisible(true);
    }

    private void init() {
        panelBody = new JPanel();
        panelBody.setBounds(0, 0, 500, 450);
        add(panelBody);

        loadUsers();
        createTable();
        inflateTable();
    }

    private void createTable() {
        scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 300, 300);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getVerticalScrollBar().addAdjustmentListener(onUserScrolls());

        panelBody.add(scrollPane);

        table = new JTable(){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }
        };
        table.setModel(new DefaultTableModel(
                new Object[][] { { null }, },
                new String[] { "New column" }
        ));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(table);
    }

    private void inflateTable() {
        defaultTableModel = new DefaultTableModel();

        defaultTableModel.addColumn("id");
        defaultTableModel.addColumn("Name");
        defaultTableModel.addColumn("Phone");
        defaultTableModel.addColumn("Status");

        paginateUsers.getData().forEach(user ->
            defaultTableModel.addRow(new String[]{
                String.valueOf(user.getId()),
                user.getName(),
                user.getPhone(),
                user.getStatus()
            })
        );

        table.setModel(defaultTableModel);
    }

    private void loadUsers() {
        int initialPage = 1;
        paginateUsers = userController.getUsers(PER_PAGE, initialPage);
    }

    private void loadMore() {
        int newPage = paginateUsers.getPage() + 1;
        if (paginateUsers.getTotalPages() >= newPage) {
            paginateUsers.setPage(newPage);
            Paginate<User> newUsers = userController.getUsers(PER_PAGE, newPage);
            paginateUsers.getData().addAll(newUsers.getData());
            System.out.println("Users founded -> " + newUsers.getData().size());
            inflateTable();
        }
    }

    private AdjustmentListener onUserScrolls() {
        return (e) -> {
            JScrollBar scrollBar = (JScrollBar) e.getAdjustable();
            int extent = scrollBar.getModel().getExtent();
            int maximum = scrollBar.getModel().getMaximum();
            int limitBeforeTouch = 50;
            if(extent + e.getValue() >= maximum - limitBeforeTouch){
                loadMore();
            }
        };
    }
}
