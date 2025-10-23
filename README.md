# AI-Powered Document Analysis Chatbot

## Project Overview
This project is a command-line-based chatbot built in Java. It uses a Retrieval-Augmented Generation (RAG) pipeline to answer natural language questions based on the content of local text documents.

It is designed as a foundational example to demonstrate the core architecture of a RAG system. For simplicity, it simulates the embedding and language generation steps, making it fully operational without external API dependencies.

## Features
*   **Document Loading**: Loads and parses text from a specified file.
*   **Text Chunking**: Splits documents into smaller, manageable paragraphs.
*   **Simulated Embeddings**: Converts text chunks into numerical vectors using a simple, repeatable algorithm.
*   **Vector Similarity Search**: Finds the most relevant document chunk for a user's query using cosine similarity.
*   **Contextual Answer Generation**: Uses the retrieved text chunk as context to formulate a response (simulated).

## How to Set Up and Run the Project

### Prerequisites
*   Java Development Kit (JDK) 11 or higher installed.

### Instructions
1.  **Create Project Directory**: Create a folder for your project (e.g., `doc-chatbot`).
2.  **Add Source Files**: Inside the project folder, create the Java source files (`Main.java`, `DocumentLoader.java`, etc.) and paste the provided code into them.
3.  **Create a Documents Folder**: Create a sub-folder named `documents`.
4.  **Add a Sample Document**: Inside the `documents` folder, create a file named `sample.txt`. Add some text with several paragraphs to this file. For example:
    ```
    The first principle of software engineering is managing complexity. Large systems become difficult to maintain if not broken down into smaller, understandable modules.

    Data structures and algorithms are the foundation of efficient software. Choosing the right data structure can dramatically impact performance.

    Generative AI, especially Large Language Models (LLMs), has revolutionized how we interact with information. Retrieval-Augmented Generation (RAG) is a technique that combines the power of LLMs with external knowledge bases to provide more accurate and factual answers.
    ```
5.  **Compile the Code**: Open a terminal or command prompt, navigate to your project's root directory, and compile the Java files:
    ```
    javac *.java
    ```
6.  **Run the Application**:
    ```
    java Main
    ```
7.  **Interact with the Chatbot**: The application will start and prompt you to enter a question. Type your question and press Enter. To stop the program, type `exit`.

## Project Structure
*   `Main.java`: The main entry point of the application. It handles the main loop for user interaction.
*   `DocumentLoader.java`: Contains the logic for reading and parsing the text files from the `documents` directory.
*   `EmbeddingModel.java`: Simulates the creation of text embeddings (vectors) and provides a function to calculate cosine similarity between them.
*   `RAGPipeline.java`: Orchestrates the entire RAG process—taking a user query, finding relevant context from the documents, and generating a response.

## Future Enhancements
This project can be extended in several ways to make it a full-featured application:
*   **Integrate a Real LLM**: Replace the simulated answer generation with API calls to a real LLM (e.g., from OpenAI, Hugging Face, or Google).
*   **Use a Vector Database**: For larger document sets, replace the in-memory map with a dedicated vector database like ChromaDB or Pinecone for more efficient similarity searches.
*   **Advanced Document Loading**: Add support for more document types like PDF and Word by integrating libraries such as Apache PDFBox.
*   **Add a Frontend**: Build a simple web-based user interface using a framework like Spring Boot.
