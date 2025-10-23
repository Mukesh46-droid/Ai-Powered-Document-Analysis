// Main.java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting AI-Powered Document Analysis Chatbot...");
        System.out.println("Loading documents...");

        // 1. Load and Chunk Documents
        DocumentLoader loader = new DocumentLoader();
        List<String> documentChunks = loader.loadDocuments("documents/sample.txt");
        if (documentChunks.isEmpty()) {
            System.out.println("No documents were loaded. Please check the 'documents/sample.txt' file.");
            return;
        }
        System.out.println(documentChunks.size() + " document chunks loaded.");

        // 2. Embed Documents
        EmbeddingModel embeddingModel = new EmbeddingModel();
        Map<Integer, double[]> documentEmbeddings = embeddingModel.embedDocuments(documentChunks);
        System.out.println("Documents embedded successfully.");

        // Initialize the RAG pipeline
        RAGPipeline ragPipeline = new RAGPipeline(embeddingModel, documentChunks, documentEmbeddings);
        Scanner scanner = new Scanner(System.in);

        // 3. Start Chat Loop
        System.out.println("\nChatbot is ready. Enter your question (or type 'exit' to quit):");
        while (true) {
            System.out.print("> ");
            String question = scanner.nextLine();

            if (question.equalsIgnoreCase("exit")) {
                break;
            }

            String answer = ragPipeline.getAnswer(question);
            System.out.println("Answer: " + answer);
        }

        System.out.println("Exiting. Thank you for using the chatbot.");
        scanner.close();
    }
}

// DocumentLoader.java
class DocumentLoader {
    public List<String> loadDocuments(String filePath) {
        List<String> documents = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            // Simple chunking strategy: split by one or more empty lines
            String[] paragraphs = content.split("\\n\\s*\\n");
            for (String paragraph : paragraphs) {
                if (!paragraph.trim().isEmpty()) {
                    documents.add(paragraph.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading document file: " + e.getMessage());
        }
        return documents;
    }
}

// EmbeddingModel.java
class EmbeddingModel {
    private static final int VECTOR_SIZE = 256;

    // A simple, dummy method to convert text to a vector.
    // In a real application, this would call an actual embedding model API.
    public double[] embed(String text) {
        double[] vector = new double[VECTOR_SIZE];
        for (char c : text.toLowerCase().toCharArray()) {
            if (c < VECTOR_SIZE) {
                vector[c]++;
            }
        }
        // Normalize the vector (L2 normalization)
        double norm = 0.0;
        for (double v : vector) {
            norm += v * v;
        }
        norm = Math.sqrt(norm);
        if (norm > 0) {
            for (int i = 0; i < vector.length; i++) {
                vector[i] /= norm;
            }
        }
        return vector;
    }

    public Map<Integer, double[]> embedDocuments(List<String> documents) {
        Map<Integer, double[]> embeddings = new HashMap<>();
        for (int i = 0; i < documents.size(); i++) {
            embeddings.put(i, embed(documents.get(i)));
        }
        return embeddings;
    }

    public double cosineSimilarity(double[] vecA, double[] vecB) {
        double dotProduct = 0.0;
        for (int i = 0; i < vecA.length; i++) {
            dotProduct += vecA[i] * vecB[i];
        }
        // Since vectors are normalized, dot product is the cosine similarity
        return dotProduct;
    }
}

// RAGPipeline.java
class RAGPipeline {
    private final EmbeddingModel embeddingModel;
    private final List<String> documents;
    private final Map<Integer, double[]> documentEmbeddings;

    public RAGPipeline(EmbeddingModel embeddingModel, List<String> documents, Map<Integer, double[]> documentEmbeddings) {
        this.embeddingModel = embeddingModel;
        this.documents = documents;
        this.documentEmbeddings = documentEmbeddings;
    }

    public String getAnswer(String question) {
        // 1. Embed the user's question
        double[] questionEmbedding = embeddingModel.embed(question);

        // 2. Retrieve the most relevant document chunk
        double maxSimilarity = -1.0;
        int bestDocIndex = -1;

        for (Map.Entry<Integer, double[]> entry : documentEmbeddings.entrySet()) {
            double similarity = embeddingModel.cosineSimilarity(questionEmbedding, entry.getValue());
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                bestDocIndex = entry.getKey();
            }
        }

        String context = (bestDocIndex != -1) ? documents.get(bestDocIndex) : "No relevant information found.";

        // 3. Generate a response using the context
        // This is a dummy generation step. A real implementation would call an LLM.
        return generateDummyResponse(question, context);
    }

    private String generateDummyResponse(String question, String context) {
        return "Based on the most relevant information found, which is: '" + context + "' ... answering your question '" + question + "' would typically involve a call to a Large Language Model. This demo simulates that step by providing the retrieved context.";
    }
}

