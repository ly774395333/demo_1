package demo1;

import java.util.Scanner;

public class IntDouble {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int age;
		double weight,height,bmi;
		Scanner scan = new Scanner(System.in);
		System.out.print("�������������䣺");
		age = scan.nextInt();
		System.out.print("�������������أ������");
		weight = scan.nextDouble();
		System.out.print("������������ߣ��ף���");
		height = scan.nextDouble();
		bmi = weight/Math.pow(height,2);
		System.out.println("BMI:"+bmi);
	}

}
