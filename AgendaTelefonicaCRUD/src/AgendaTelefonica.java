import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendaTelefonica {

    private static final String URL = ConfigLoader.getProperty("db.url");
    private static final String USER = ConfigLoader.getProperty("db.user");
    private static final String PASSWORD = ConfigLoader.getProperty("db.password");

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private Contato extrairContatoDoResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        String telefone = rs.getString("telefone");
        String email = rs.getString("email");
        return new Contato(id, nome, telefone, email);
    }

    public void adicionarContato(Contato contato) {
        String sql = "INSERT INTO contatos (nome, telefone, email) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contato.getNome());
            pstmt.setString(2, contato.getTelefone());
            pstmt.setString(3, contato.getEmail());
            pstmt.executeUpdate();
            System.out.println("Contato adicionado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar contato: " + e.getMessage());
        }
    }

    public List<Contato> listarContatos() {
        List<Contato> contatos = new ArrayList<>();
        String sql = "SELECT * FROM contatos ORDER BY nome";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                contatos.add(extrairContatoDoResultSet(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar contatos: " + e.getMessage());
        }
        return contatos;
    }

    public List<Contato> buscarContatosPorNome(String nomeBusca) {
        List<Contato> contatosEncontrados = new ArrayList<>();
        String sql = "SELECT * FROM contatos WHERE nome LIKE ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + nomeBusca + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    contatosEncontrados.add(extrairContatoDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar contatos: " + e.getMessage());
        }
        return contatosEncontrados;
    }

    public Contato buscarContatoPorId(int idBusca) {
        String sql = "SELECT * FROM contatos WHERE id = ?";
        Contato contato = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idBusca);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    contato = extrairContatoDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar contato por ID: " + e.getMessage());
        }
        return contato;
    }

    public void atualizarContato(Contato contato) {
        String sql = "UPDATE contatos SET nome = ?, telefone = ?, email = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contato.getNome());
            pstmt.setString(2, contato.getTelefone());
            pstmt.setString(3, contato.getEmail());
            pstmt.setInt(4, contato.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Contato atualizado com sucesso!");
            } else {
                System.out.println("Nenhum contato encontrado com o ID fornecido para atualizar.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar contato: " + e.getMessage());
        }
    }

    public void removerContato(int id) {
        String sql = "DELETE FROM contatos WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Contato removido com sucesso!");
            } else {
                System.out.println("Nenhum contato encontrado com o ID fornecido.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao remover contato: " + e.getMessage());
        }
    }
}