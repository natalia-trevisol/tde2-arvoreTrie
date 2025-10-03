public class Main {
    public static void main(String[] args) {
        ArvoreTrie trie = new ArvoreTrie();

        // Inserindo palavras
        trie.inserir("carro");
        trie.inserir("casa");
        trie.inserir("cachorro");
        trie.inserir("doce");
        trie.inserir("dono");
        trie.inserir("dom");

        // Buscas
        System.out.println("Buscar 'casa': " + trie.buscar("casa"));   // true
        System.out.println("Buscar 'cavalo': " + trie.buscar("cavalo")); // false

        // Impressão
        System.out.println("\nPalavras armazenadas:");
        trie.imprimirTodasPalavras();

        // Remoção
        System.out.println("\nRemovendo 'dom'...");
        trie.remover("dom");
        System.out.println("Buscar 'dom': " + trie.buscar("dom")); // false

        System.out.println("\nApós remoção:");
        trie.imprimirTodasPalavras();

        // Sugestões
        System.out.println("\nSUGESTÕES AUTOMÁTICAS (exemplo prático)\n");
        System.out.println("Sugestões para 'ca':");
        trie.sugestoesPorPrefixo("ca");

        System.out.println("\nSugestões para 'do':");
        trie.sugestoesPorPrefixo("do");

        System.out.println("\nSugestões para 'z':");
        trie.sugestoesPorPrefixo("z");


    }
}

