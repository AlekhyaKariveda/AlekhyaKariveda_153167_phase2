package com.cg.mypaymentapp.pl;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import java.util.Scanner;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transactions;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;

public class Client{
	private WalletService walletService;
	//private Map<String, Customer> data;
	public Client() {
		System.out.println("=========Welcome to Payment Wallet Application==========");
		Customer customer = new Customer();
		walletService = new WalletServiceImpl();	
	}
	public void operations(){		
		System.out.println("1) Create New Paytm Account");
		System.out.println("2) Check Your Balance");
		System.out.println("3) Deposit Amount");
		System.out.println("4) Withdraw Amount");
		System.out.println("5) Transfer Funds");
		System.out.println("6) Show All Transactions");
		System.out.println("7) Exit Application");
		System.out.println("\nEnter Your Choice");
	
		Scanner console=new Scanner(System.in);
		
		String mobileNo;
		String mobileNo1;
		BigDecimal amount;
		String name;
		Customer customer;
		switch (console.nextInt()){
			case 1:
			System.out.println("Enter Your Name          : ");
			name=console.next();
			
			System.out.println("Enter Your Mobile Number : ");
			mobileNo=console.next();
			
			System.out.println("Enter Balance            : ");
			amount=console.nextBigDecimal();	
			try {
				customer =  walletService.createAccount(name, mobileNo, amount);	
				System.out.println("Thank you, "+customer.getName()+" Your Payment wallet account has been created successfully with Balance "+amount);
			} 
			catch (Exception e) {
				System.out.println(e.getMessage());					
			}
			break;
		case 2:
			System.out.println("Enter your registered mobile Number:\n");
			mobileNo = console.next();
			try {
				customer =  walletService.showBalance(mobileNo);
				System.out.println("Name: "+ customer.getName());
				System.out.println("Your account balance: "+ customer.getWallet().getBalance());
			}
			catch (InvalidInputException | InsufficientBalanceException e)
			{
				System.out.println(e.getMessage());	
			}
			break;
		
		case 3:
	
			System.out.print("Enter your registered mobile Number: ");
			mobileNo = console.next();
			
			System.out.print("Enter the Amount to Deposit: ");
			amount = console.nextBigDecimal();
			try {
				customer =  walletService.depositAmount(mobileNo, amount);
				System.out.println("Amount " + amount + " deposited in the Account with customer name : " + customer.getName());
				System.out.println("Updated balance is " + customer.getWallet().getBalance());
			} 
			catch (InvalidInputException | InsufficientBalanceException e) {
				System.out.println(e.getMessage());	
			}
			break;
		case 4:
			System.out.print("Enter your registered mobile Number: ");
			mobileNo = console.next();
			System.out.print("Enter the Amount to Withdrawn: ");
			amount = console.nextBigDecimal();
			try {
				customer =  walletService.withdrawAmount(mobileNo, amount);
				System.out.println("Amount " + amount + " withdrawn from the Account with customer name : " + customer.getName());
				System.out.println("Updated balance is " + customer.getWallet().getBalance());
			} catch (InvalidInputException | InsufficientBalanceException e) {
				System.out.println(e.getMessage());	
			}
			break;
		case 5:
			System.out.print("Enter the Source Mobile Number :  ");
			mobileNo = console.next();
			
			System.out.print("Enter the Destination mobile number : ");
			mobileNo1 = console.next();
			
			System.out.print("Enter the amount  : ");
			amount = console.nextBigDecimal();
			try {
				customer =  walletService.fundTransfer(mobileNo, mobileNo1, amount);
				System.out.println("Amount " + amount + " Transferred from the Account with customer name : " + customer.getName());
				System.out.println("Updated balance is " + customer.getWallet().getBalance());
			} 
			catch (InvalidInputException | InsufficientBalanceException e) {
				System.out.println(e.getMessage());	
			}
			break;
		case 6:
			Transactions transactions = new Transactions();
			List<String> transactionDetails1 = new ArrayList<String>();
			System.out.print("Enter the Mobile number: ");
			mobileNo = console.next();
			
			transactions = walletService.getTransaction(mobileNo);
			transactionDetails1 = transactions.getTransactionDetails();
			//if(transactionDetails1!=null)
			try
			{
			Iterator<String> it = transactionDetails1.iterator();
			while(it.hasNext())
				System.out.println(it.next());
			}
			catch(Exception e) {
				System.out.println("Unable to find any transactions.");
			}
			break;
		case 7:
			System.out.println("Thank you for using Payment Wallet Application");
        	System.exit(0);
        	break;
		default:
		       System.out.println("Kindly enter right choice.Invalid choice");
			break;
		}
	}
    public static void main(String[] args){
    	Scanner console = new Scanner(System.in);
		Client client = new Client();
		String choice = "yes";
		do
		{
			if(choice.equalsIgnoreCase("yes")||choice.equalsIgnoreCase("y"))
			{
			client.operations();
			System.out.print("Do you want to continue(yes/no): ");
			choice = console.next();
			}
		}while(choice.equalsIgnoreCase("yes")||choice.equalsIgnoreCase("y"));
		
		System.out.println("Thank you for using it.");
		System.exit(0);
		console.close();
	}
}