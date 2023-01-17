package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.entites.Product;

public class Program {

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		List<Product> list = new ArrayList<>();
		
		System.out.print("Enter a file path: ");
		String sourceFileStr = sc.nextLine();
		
		File file = new File(sourceFileStr);
		String sourceFolderStr = file.getParent();
		
		//cria nova pasta 
		boolean success = new File(sourceFileStr + "\\out").mkdir();
		
		//cria novo arquivo de nome "summary.csv" a partir da pasta
		String targetFileStr = sourceFolderStr + "\\out\\summary.csv";			
		
		try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))) {
			
			String itemCsv = br.readLine();
			
			while (itemCsv != null) {
				//separa a string utilizando a "," como separador e à acrescenta a um vetor
				String[] vect = itemCsv.split(",");
				//nome ocupando a posicao 0
				String name = vect[0];
				//price ocupando a posicao 1, mas precisa ser convertida primeiro por ser originalmente uma string
				double price = Double.parseDouble(vect[1]);
				//quantity ocupando a posicao 2, mas precisa ser convertida primeiro por ser originalmente uma string
				int quantity = Integer.parseInt(vect[2]);
				
				//insere o produto à lista e instancia um novo produto
				list.add(new Product(name, price, quantity));
				
				itemCsv = br.readLine();
			}
			
		
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))) {
				
				for(Product item : list) {
					bw.write(item.getName() + "," + String.format("%.2f", item.calculateTotal()));
					bw.newLine();
				}
				
				System.out.println(targetFileStr +  " CREATED!");
				
			} catch (IOException e) {
				System.out.println("error:  " + e.getMessage());
			}
			
		 sc.close();

	}
}
}

