package test;
import java.util.Scanner;

import javax.swing.JFrame;

import ui.LoginView;

public class TestJson {
	public static void main(String[] args) {
		JFrame frame = new LoginView();
		System.out.println(frame instanceof LoginView);
		
//		Gson gson = new Gson();
//		JsonObject json = new JsonObject();
//		json.addProperty("operation", "SetName");
//		System.out.println(json.toString());
//		Order order = new Order();
//		order.setOper("ChangeName");
//		order.setOldName("张三");
//		order.setNewName("李四");
//		String jsonStr = gson.toJson(order);
//		System.out.println(jsonStr);
//		JsonObject j = gson.fromJson(jsonStr, JsonObject.class);
//		String oper = j.get("oldName").getAsString();
//		System.out.println(oper);
//		int id = 999999;
//		System.out.println(id);
		
		Scanner scanner = new Scanner(System.in);
		int sum = scanner.nextInt();
//		int tmp = sum/3;
		int a[] = new int[10];
		for(int i = 0; i < 10; i++) {
			a[i] = scanner.nextInt();
		}
//		for(int i = 0; i < 9; i++) {
//			for(int j = i + 1; j < 10; j++) {
//				if(a[j] <= a[i]) {
//					int k = a[j]^a[i];
//					a[j] = a[j]^k;
//					a[i] = a[i]^k;
//				}
//			}
//			if(a[i] >= tmp);
//		}
		func(a, 0, 4, 10, sum);
		scanner.close();
	}
	public static void func(int a[],int k, int m,int n, int sum){
		if(k==m-1){
			int i;
			int tmp=0;
			for(i=0;i<m-1;i++)
				tmp+=a[i];
			if(tmp==sum) {
				for(i=0;i<m-1;i++)
					System.out.print(String.format("%3d", a[i]));
			System.out.println();
			}
		}else{
			int i;
			for(i=k;i<n;i++){
				int tmp=a[k];
				a[k]=a[i];
				a[i]=tmp;
				func(a,k+1,m, n, sum);
				tmp=a[k];
				a[k]=a[i];
				a[i]=tmp;
			} 
		}
	}
}
