import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
class Bank implements ActionListener
{
	JFrame f;
	JDesktopPane desktop;
	JInternalFrame new_account,close_account,deposit,withdraw;
	JLabel account_no,name,amount,balance;
	JButton save,delete,close,update,cancel;
	JTextField an1,nme1,amt1,an2,nme2,bal2,an3,nme3,amt3,bal3,an4,nme4,amt4,bal4;
	String accountItems[]={"New Account","Close Account","Exit"};
	String transactionItems[]={"Deposit","Withdraw"};
	int rp;
	Connection con;
	Statement stmt;
	public Bank()
	{
		f=new JFrame();
		desktop=new JDesktopPane();
		JMenuBar mb=new JMenuBar();
		JMenu accountMenu=new JMenu("Account");
		JMenu transactionMenu=new JMenu("Transaction");
		 for(int i=0;i<accountItems.length;i++)
        {
			JMenuItem item=new JMenuItem(accountItems[i]);
			item.addActionListener(this);
			accountMenu.add(item);
		}			
		for(int i=0;i<transactionItems.length;i++)
		{
			JMenuItem item=new JMenuItem(transactionItems[i]);
			item.addActionListener(this);
			transactionMenu.add(item);
		}
		mb.add(accountMenu);
		mb.add(transactionMenu);
		f.setJMenuBar(mb);
		f.add(desktop);
		f.setSize(600,600);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		try
		{
				Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3306/bank?user=root&password=root";
		con=DriverManager.getConnection(url);
		stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);	
		}
		catch(Exception sq){
			sq.printStackTrace();
		}

	}
	public void actionPerformed(ActionEvent e)
	{
		String str=e.getActionCommand();
		if(str.equals("New Account"))
		{
			new_account=new JInternalFrame("Create New Account",true,true,true,true);
			new_account.setSize(270,150);
			new_account.setResizable(false);
			new_account.setLayout(new FlowLayout());
			account_no=new JLabel("Account No:");
			new_account.add(account_no);
			an1=new JTextField(15);
			an1.setEditable(false);
			new_account.add(an1);
			name=new JLabel("Name:");
			new_account.add(name);
			nme1=new JTextField(15);
			new_account.add(nme1);
			amount=new JLabel("Amount:");
			new_account.add(amount);
			amt1=new JTextField(15);
			new_account.add(amt1);
			save=new JButton("Save");
			new_account.add(save);
			save.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					int flag=0;
					for(int i=0;i<(nme1.getText()).length();i++)
					{
						String s=nme1.getText();
						char ch=s.charAt(i);
						if(ch>=48 && ch<=57)
						{JOptionPane.showMessageDialog(f,"name cant be numeric!!","error",JOptionPane.INFORMATION_MESSAGE);flag=1;break;}
					}
					for(int i=0;i<(amt1.getText()).length();i++)
					{
						String s=amt1.getText();
						char ch=s.charAt(i);
						if((ch>=65 && ch<=90)|| (ch>=97 && ch<=122))
						{
							JOptionPane.showMessageDialog(f,"amount cant be in character!!","error",JOptionPane.INFORMATION_MESSAGE);
							flag=1;
							break;
						}
					   
					}
					if(flag==0)
					{
						try
					{

			           stmt.executeUpdate("INSERT into bank values('"+an1.getText()+"','"+nme1.getText()+"','"+amt1.getText()+"')");
					   stmt.executeUpdate("update accounts set no_of_accounts='"+rp+"' where no_of_accounts='"+(rp-1)+"'");
					   JOptionPane.showMessageDialog(f,"account created sucessfully!!","Success",JOptionPane.INFORMATION_MESSAGE);
					   rp=rp+1;
			           an1.setText(""+rp);
			           nme1.setText("");
			           amt1.setText("");
					}
					catch(Exception s)
					{
						s.printStackTrace();
						System.out.println(s.getMessage());
					}
					}
   					 
				}
			});
			cancel=new JButton("Cancel");
			cancel.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					nme1.setText("");
					amt1.setText("");
				}
			});
			new_account.add(cancel);
			close=new JButton("Close");
			close.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					new_account.setVisible(false);
				}
			});
			new_account.add(close);
			try
			{
			     ResultSet rs=stmt.executeQuery("SELECT no_of_accounts from accounts");
			     while(rs.next())
			     rp=rs.getInt("no_of_accounts");
			     rp=rp+1;
			     an1.setText(""+rp);
			}
			catch(Exception n)
			{
				System.out.println(n.getMessage());
			}
			desktop.add(new_account);
			new_account.setVisible(true);
		}
		else if(str.equals("Close Account"))
		{
			close_account=new JInternalFrame("Close Account",true,true,true,true);
			close_account.setSize(270,150);
			close_account.setResizable(false);
			close_account.setLayout(new FlowLayout());
			account_no=new JLabel("Account No:");
			close_account.add(account_no);
			an2=new JTextField(15);
			close_account.add(an2);
			name=new JLabel("Name:");
			close_account.add(name);
			nme2=new JTextField(15);
		    nme2.setEditable(false);
			close_account.add(nme2);
			balance=new JLabel("Balance:");
			close_account.add(balance);
			bal2=new JTextField(15);
			bal2.setEditable(false);
			close_account.add(bal2);
				an2.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
			     try
			    {
			     ResultSet rs=stmt.executeQuery("SELECT name,balance from bank where Account_no='"+an2.getText()+"'");
			     rs.next();
			     rp=rs.getRow();
			     nme2.setText(rs.getString("Name"));
			     bal2.setText(rs.getString("Balance"));
			    
			   }
			    catch(Exception u)
			   {
				System.out.println(u.getMessage());
				if(u.getMessage().equals("Illegal operation on empty result set."))
			    JOptionPane.showMessageDialog(f," sorry account does not exist!!!","ERROR!",JOptionPane.INFORMATION_MESSAGE);
			   }
			 }
			});
			
			delete=new JButton("Delete");
			delete.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					try
					{
			           String num=an2.getText();
					   String n1=nme2.getText();
					   String n2=bal2.getText();
			           stmt.executeUpdate("delete from bank where Account_no='"+an2.getText()+"'");
					   JOptionPane.showMessageDialog(f,"account is closed sucessfully!!","Success",JOptionPane.INFORMATION_MESSAGE);
					   if(deposit.isVisible())
					   {
					   	  if(an3.getText().equals(an2.getText()))
			                {
			                	nme3.setText("");
			                    bal3.setText("");
			                    an3.setText("");
			                    amt3.setText("");
			                }
					   }
					   if(withdraw.isVisible())
					   {
			                if(an4.getText().equals(an2.getText()))
			                {
			                	nme4.setText("");
			                    bal4.setText("");
			                    an4.setText("");
			                    amt4.setText("");
			                }
					   }
					   an2.setText("");
					   nme2.setText("");
					   bal2.setText("");			            
					}
					catch(Exception d)
					{
					    System.out.println(d.getMessage());	
					}
				}
			});
			close_account.add(delete);
			cancel=new JButton("Cancel");
			cancel.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					an2.setText("");
					nme2.setText("");
					bal2.setText("");
				}
			});
			close_account.add(cancel);
			close=new JButton("Close");
			close.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					close_account.setVisible(false);
				}
			});
			close_account.add(close);
			desktop.add(close_account);
			close_account.setVisible(true);
		}
		else if(str.equals("Exit"))
		{
			System.exit(1);
		}
		else if(str.equals("Deposit"))
		{
			deposit=new JInternalFrame("Deposit",true,true,true,true);
			deposit.setSize(270,180);
			deposit.setResizable(false);
			deposit.setLayout(new FlowLayout());
			account_no=new JLabel("Account No:");
			deposit.add(account_no);
			an3=new JTextField(15);
			deposit.add(an3);
			name=new JLabel("Name:");
			deposit.add(name);
			nme3=new JTextField(15);
			nme3.setEditable(false);
			deposit.add(nme3);
			balance=new JLabel("Balance:");
			deposit.add(balance);
			bal3=new JTextField(15);
		     bal3.setEditable(false);
			deposit.add(bal3);
				an3.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
			     try
			    {
			     ResultSet rs=stmt.executeQuery("SELECT name,balance from bank where Account_no='"+an3.getText()+"'");
			     rs.next();
			     nme3.setText(rs.getString("Name"));
			     bal3.setText(rs.getString("Balance"));
			   }
			    catch(Exception u)
			   {
				System.out.println(u.getMessage());
				if(u.getMessage().equals("Illegal operation on empty result set."))
			    JOptionPane.showMessageDialog(f," sorry account does not exist!!!","ERROR!",JOptionPane.INFORMATION_MESSAGE);
			   }
				}
			});
			amount=new JLabel("Amount:");
			deposit.add(amount);
			amt3=new JTextField(15);
			deposit.add(amt3);
			update=new JButton("Update");
			update.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					try
						{
					   String n2=amt3.getText();
					   Integer b=new Integer(n2);
					   int a2=b;
					   String n1=bal3.getText();
					   b=new Integer(n1);
					   int a1=b;
					   a1+=a2; 
			           stmt.executeUpdate("update bank set name='"+nme3.getText()+"',balance='"+a1+"' where account_no='"+an3.getText()+"'");
					   JOptionPane.showMessageDialog(f,"Deposited sucessfully!!","Success",JOptionPane.INFORMATION_MESSAGE);
					    bal3.setText(""+a1);
					    amt3.setText("");
					    if(withdraw.isVisible())
					    {
					    	ResultSet rsn=stmt.executeQuery("SELECT name,balance from bank where Account_no='"+an4.getText()+"'");
			                rsn.next();
			              nme4.setText(rsn.getString("Name"));
			             bal4.setText(rsn.getString("Balance"));
			    
					    }
					    if(close_account.isVisible())
					    {
					    	ResultSet rs1=stmt.executeQuery("SELECT name,balance from bank where Account_no='"+an2.getText()+"'");
			                rs1.next();
			                rp=rs1.getRow();
			                nme2.setText(rs1.getString("Name"));
			                bal2.setText(rs1.getString("Balance"));
					    }
					    }
					    catch(Exception u)
					    {
					    	System.out.println(u.getMessage());
					    }	
				}
			});
			deposit.add(update);
			cancel=new JButton("Cancel");
			cancel.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					an3.setText("");
					bal3.setText("");
					amt3.setText("");
					nme3.setText("");
				}
			}
			);
			deposit.add(cancel);
			close=new JButton("Close");
			deposit.add(close);
			close.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					deposit.setVisible(false);
				}
			});
			desktop.add(deposit);
			deposit.setVisible(true);
		}
		else if(str.equals("Withdraw"))
		{
			withdraw=new JInternalFrame("WithDraw",true,true,true,true);
			withdraw.setSize(270,180);
			withdraw.setResizable(false);
			withdraw.setLayout(new FlowLayout());
			account_no=new JLabel("Account No:");
			withdraw.add(account_no);
			an4=new JTextField(15);
			withdraw.add(an4);
			name=new JLabel("Name:");
			withdraw.add(name);
			nme4=new JTextField(15);
			nme4.setEditable(false);
			withdraw.add(nme4);
			balance=new JLabel("Balance:");
			withdraw.add(balance);
			bal4=new JTextField(15);
			bal4.setEditable(false);
			withdraw.add(bal4);
			an4.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
			     try
			    {
			     ResultSet rs=stmt.executeQuery("SELECT name,balance from bank where Account_no='"+an4.getText()+"'");
			     rs.next();
			     nme4.setText(rs.getString("Name"));
			     bal4.setText(rs.getString("Balance"));
			   }
			    catch(Exception u)
			   {
				System.out.println(u.getMessage());
				if(u.getMessage().equals("Illegal operation on empty result set."))
			    JOptionPane.showMessageDialog(f," sorry account does not exist!!!","ERROR!",JOptionPane.INFORMATION_MESSAGE);
			   }
				}
			});
			amount=new JLabel("Amount:");
			withdraw.add(amount);
			amt4=new JTextField(15);
			withdraw.add(amt4);
			update=new JButton("Update");
			update.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					try
						{
					    
					   con.setAutoCommit(false);
					   String n2=amt4.getText();
					   Integer b=new Integer(n2);
					   int a2=b;
					   String n1=bal4.getText();
					   b=new Integer(n1);
					   int a1=b;
					   a1-=a2; 
			           stmt.executeUpdate("update bank set name='"+nme4.getText()+"',balance='"+a1+"' where account_no='"+an4.getText()+"'");
					   con.commit();
					   JOptionPane.showMessageDialog(f,"amount is withdrawal sucessfully!!","Success",JOptionPane.INFORMATION_MESSAGE);
		                bal4.setText(""+a1);
					    amt4.setText("");
					    if(deposit.isVisible())
					    {
					      ResultSet rsn=stmt.executeQuery("SELECT name,balance from bank where Account_no='"+an3.getText()+"'");
			              rsn.next();
			                nme3.setText(rsn.getString("Name"));
			                bal3.setText(rsn.getString("Balance"));
					    }
					    if(close_account.isVisible())
					    {
					    	ResultSet rs1=stmt.executeQuery("SELECT name,balance from bank where Account_no='"+an2.getText()+"'");
			                rs1.next();
			                rp=rs1.getRow();
			                nme2.setText(rs1.getString("Name"));
			                bal2.setText(rs1.getString("Balance"));
					    }			   
 					    }
					    catch(Exception u)
					    {
					    	System.out.println(u.getMessage());	
					    }
				}
			});
			withdraw.add(update);
			cancel=new JButton("Cancel");
			cancel.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					an4.setText("");
					bal4.setText("");
					amt4.setText("");
					nme4.setText("");
				}
			}
			);
			withdraw.add(cancel);
			close=new JButton("Close");
			withdraw.add(close);
			close.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					withdraw.setVisible(false);
				}
			}
			);
			desktop.add(withdraw);
			withdraw.setVisible(true);
		}
	}
	public static void main(String args[])
	{
		Bank bank=new Bank();
	}
}