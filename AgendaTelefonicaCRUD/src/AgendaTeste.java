import java.util.List;
import java.util.Scanner;

public class AgendaTeste {

    private static final Scanner scanner = new Scanner(System.in);
    private static final AgendaTelefonica agenda = new AgendaTelefonica();

    public static void main(String[] args) {
        int opcao = 0;
        while (opcao != 6) {
            exibirMenu();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                rotearOpcao(opcao);

            } catch (NumberFormatException e) {
                System.out.println("❌ Erro: Por favor, digite um número válido para a opção.");
            } catch (Exception e) {
                System.out.println("❌ Ocorreu um erro inesperado: " + e.getMessage());
            }
        }
        System.out.println("Programa finalizado. Até logo!");
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n--- AGENDA TELEFÔNICA ---");
        System.out.println("1. Adicionar um novo contato");
        System.out.println("2. Remover um contato existente");
        System.out.println("3. Buscar contatos pelo nome");
        System.out.println("4. Atualizar um contato existente");
        System.out.println("5. Listar todos os contatos");
        System.out.println("6. Sair do programa");
        System.out.print("➡️ Escolha uma opção: ");
    }

    private static void rotearOpcao(int opcao) {
        switch (opcao) {
            case 1:
                adicionarNovoContato();
                break;
            case 2:
                removerUmContato();
                break;
            case 3:
                buscarContatos();
                break;
            case 4:
                atualizarUmContato();
                break;
            case 5:
                listarTodosOsContatos();
                break;
            case 6:
                break;
            default:
                System.out.println("❌ Opção inválida. Tente novamente.");
                break;
        }
    }

    private static void adicionarNovoContato() {
        System.out.println("\n--- Adicionar Novo Contato ---");
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Digite o email: ");
        String email = scanner.nextLine();

        if (nome.trim().isEmpty()) {
            System.out.println("❌ O nome não pode ser vazio.");
            return;
        }

        agenda.adicionarContato(new Contato(nome, telefone, email));
    }

    private static void removerUmContato() {
        System.out.println("\n--- Remover Contato ---");
        System.out.print("Digite o ID do contato a ser removido: ");
        try {
            int idRemover = Integer.parseInt(scanner.nextLine());
            agenda.removerContato(idRemover);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Por favor, digite um número.");
        }
    }

    private static void buscarContatos() {
        System.out.println("\n--- Buscar Contatos por Nome ---");
        System.out.print("Digite o nome ou parte do nome a ser buscado: ");
        String nomeBusca = scanner.nextLine();

        List<Contato> contatosEncontrados = agenda.buscarContatosPorNome(nomeBusca);

        if (contatosEncontrados.isEmpty()) {
            System.out.println("Nenhum contato encontrado com o termo '" + nomeBusca + "'.");
        } else {
            System.out.println("--- Contatos Encontrados ---");
            for (Contato c : contatosEncontrados) {
                System.out.println(c);
            }
        }
    }

    private static void atualizarUmContato() {
        System.out.println("\n--- Atualizar Contato ---");
        System.out.print("Digite o ID do contato a ser atualizado: ");

        try {
            int idAtualizar = Integer.parseInt(scanner.nextLine());
            Contato contatoParaAtualizar = agenda.buscarContatoPorId(idAtualizar);

            if (contatoParaAtualizar == null) {
                System.out.println("❌ Contato não encontrado com o ID informado.");
                return;
            }

            System.out.println("Deixe o campo em branco para não alterar a informação atual.");

            System.out.print("Novo nome (" + contatoParaAtualizar.getNome() + "): ");
            String novoNome = scanner.nextLine();

            System.out.print("Novo telefone (" + contatoParaAtualizar.getTelefone() + "): ");
            String novoTelefone = scanner.nextLine();

            System.out.print("Novo email (" + contatoParaAtualizar.getEmail() + "): ");
            String novoEmail = scanner.nextLine();

            if (!novoNome.trim().isEmpty()) contatoParaAtualizar.setNome(novoNome);
            if (!novoTelefone.trim().isEmpty()) contatoParaAtualizar.setTelefone(novoTelefone);
            if (!novoEmail.trim().isEmpty()) contatoParaAtualizar.setEmail(novoEmail);

            agenda.atualizarContato(contatoParaAtualizar);

        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido. Por favor, digite um número.");
        }
    }

    private static void listarTodosOsContatos() {
        List<Contato> contatos = agenda.listarContatos();

        System.out.println("\n--- Lista de Todos os Contatos ---");
        if (contatos.isEmpty()) {
            System.out.println("A agenda está vazia.");
        } else {
            for (Contato c : contatos) {
                System.out.println(c);
            }
        }
    }
}