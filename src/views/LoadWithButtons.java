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
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentListener;
import java.util.IllegalFormatException;

public class LoadWithButtons extends JFrame {

    private static final int WINDOW_WIDTH = 510;
    private static final int WINDOW_HEIGHT = 550;
    private static final int INITIAL_PAGE = 1;
    private static final int INITIAL_PER_PAGE = 12;

    private JPanel panelBody;
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel defaultTableModel;

    private UserController userController;
    private Paginate<User> paginateUsers;

    public static void main(String[] args) {
        new LoadWithButtons();
    }

    public LoadWithButtons () {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        init();

        setVisible(true);
    }

    private void init() {
        normalizeDatabase();
        userController = new UserController();

        panelBody = new JPanel();
        panelBody.setBounds(0, 0, 500, 500);
        add(panelBody);

        loadUsers();
        createTable();
        inflateUsersInTable();
        addPaginationBar();
    }

    public void normalizeDatabase() {
        DBConnection.getConnection();

        if (DBConnection.hasBeenRecreated) {
            MigrationBuilder.migrate();
            SeederBuilder.seed();
        }
    }

    private void loadUsers () {
        paginateUsers = userController.getUsers(INITIAL_PER_PAGE, INITIAL_PAGE);
    }

    private void createTable () {
        scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 300, 150);
        scrollPane.setBackground(Color.WHITE);
        panelBody.add(scrollPane);

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][] { { null }, },
                new String[] { "New column" }
        ));
        scrollPane.setViewportView(table);
    }

    private void inflateUsersInTable () {
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

    private void addPaginationBar() {
        JPanel paginationBar = new JPanel();
        paginationBar.setLayout(new FlowLayout());
        paginationBar.setLocation(0, 470);

        JButton btnNext = new JButton("Next");
        JLabel currentPage = new JLabel("1");
        JButton btnPrev = new JButton("Prev");

        paginationBar.add(btnPrev);
        paginationBar.add(currentPage);
        paginationBar.add(btnNext);

        panelBody.add(paginationBar);

        btnNext.addActionListener(onNext(currentPage));
        btnPrev.addActionListener(onPrev(currentPage));
    }

    public ActionListener onNext(JLabel currentPage) {
        return e -> {
            int newPage = paginateUsers.getPage() + 1;
            if (newPage <= paginateUsers.getTotalPages()) {
                currentPage.setText(String.valueOf(newPage));
                loadMore(newPage);
            }
        };
    }

    public ActionListener onPrev(JLabel currentPage) {
        return e -> {
            int newPage = paginateUsers.getPage() - 1;
            if (newPage >= INITIAL_PAGE) {
                currentPage.setText(String.valueOf(newPage));
                loadMore(newPage);
            }
        };
    }

    private void loadMore(int newPage) {
        paginateUsers.setPage(newPage);
        Paginate<User> newUsers = userController.getUsers(paginateUsers.getPerPage(), newPage);
        paginateUsers.setData(newUsers.getData());
        inflateUsersInTable();
    }
}
