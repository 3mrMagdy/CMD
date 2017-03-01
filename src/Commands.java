import java.io.File;
import java.nio.file.*;
import java.util.*;

public class Commands
{
	public String def=System.getProperty("user.dir"), key="user.dir";
	
	public static void main (String arg[])
	{
		Scanner in = new Scanner (System.in);
		Commands C = new Commands();
		
		while (true)
		{
			String com = in.nextLine();
			String coms[] = com.split(";");
			for (String x : coms)
			{		

				if (x.equals("date"))
					C.date();
				
				else if (x.equals("args"))
					C.args("");
				
				else if (x.equals("clear"))
					C.clear();
				
				else if (x.equals("pwd"))
					C.pwd();

				else if(x.charAt(0)=='?' || x.equals("help"))
					C.help(x.equals("help") || x.length()==1 ? "" : x.substring(2));
				
				else if (x.length()>=2 && x.substring(0,2).equals("cd"))
					C.cd(x.length()==2 ? "" : x.substring(3));
				
				else if (x.length()>=2 && x.substring(0,2).equals("ls"))
					C.ls(x.length()==2 ? "" : x.substring(3));
				
				else if (x.length()>=2 && x.substring(0,2).equals("cp"))
					C.cp(x.length()==2 ? "" : x.substring(3));
				
				else if (x.length()>=2 && x.substring(0,2).equals("mv"))
					C.mv(x.length()==2 ? "" : x.substring(3));

				else if (x.length()>=5 && x.substring(0,5).equals("rmdir"))
					C.rmdir(x.length()==5 ? "" : x.substring(6));

				else if (x.length()>=2 && x.substring(0,2).equals("rm"))
					C.rm(x.length()==2 ? "" : x.substring(3));

				else if (x.length()>=3 && x.substring(0,3).equals("cat"))
					C.cat(x.length()==3 ? "" : x.substring(4));

				else if (x.length()>=4 && x.substring(0,4).equals("more"))
					C.more(x.length()==4 ? "" : x.substring(5));

				else if (x.length()>=5 && x.substring(0,5).equals("mkdir"))
					C.mk_dir(x.length()==5 ? "" : x.substring(6));
				
				else if (x.equals("exit"))
					System.exit(0);
				
				else
					System.out.println("Invalid command");
			}
		}		
	}
	
	public void date ()
	{
	    java.util.Date date = new java.util.Date();
	    System.out.println(date);
	}

	public void args (String com)// some commands
	{
		if (com.equals(""))
			System.out.println("?, date, args, clear, cd, ls, cp, mv, rm, mkdir, "
					+ "rmdir, cat, more, pwd, help, exit, nothing.");
		
		else if (com.equals("?"))
			System.out.println("?, help, date, args, exit, clear, cd, ls, "
					+ "cp, mv, rm, mkdir, rmdir, cat, more, pwd.");
		
		else if (com.equals("cd"))
			System.out.println("~, .., Path, help, exit, nothing.");

		else if (com.equals("ls"))
			System.out.println("-al, help, exit, nothing.");
		
		else if (com.equals("cp"))
			System.out.println("myfile yourfile, -i myfile yourfile, -i /data/myfile, help, exit.");
		
		else if (com.equals("mv"))
			System.out.println("-i myfile yourfile, -i /data/myfile, help, exit.");

		else if (com.equals("rm"))
			System.out.println("dirpath, filepath, help, exit.");

		else if (com.equals("mkdir"))
			System.out.println("dirpath, help, exit.");

		else if (com.equals("rmdir"))
			System.out.println("dirpath, help, exit.");

		else if (com.equals("cat"))
			System.out.println(", help, exit.");

		else if (com.equals("more"))
			System.out.println(", help, exit.");
		
		else
			System.out.println("Invalid command");
	}

	public void clear()
	{
		for(int i=0 ; i<1000 ; i++)
			System.out.println("");
	}

	public void pwd ()
	{
		System.out.println(System.getProperty(key));
	}

	public void help (String com)
	{
		System.out.println("args : List all command arguments\n"
				+ "date :  Current date/time\nexit  : Stop all");
		
		Scanner input = new Scanner (System.in);
		String choose = input.nextLine();
		
		if(choose.equals("args"))
			new Commands().args(com);
		
		else if (choose.equals("date"))
			new Commands().date();
		
		else if(choose.equals("exit"))
			System.exit(0);
		
		else 
			System.out.println("Invalid choice");
		
	}

	public void cd (String com)
	{
		if (com.equals("exit"))
			System.exit(0);

		if (com.equals("help"))
			new Commands().help("cd");
				
		else if (com.equals("~") || com.equals(""))
			System.setProperty(key,def);
		
		else if (com.equals(".."))
			System.setProperty(key,new File (System.getProperty(key)).getParent());
		
		else
		{
			if(com.charAt(0)=='/')
				com = com.substring(1);
			else
				com = System.getProperty(key)+'\\'+com;
			
			if(new File (com).isDirectory())
				System.setProperty(key,com);
			else
				System.out.println("Invalid Path");
		}
	}
	
	public void ls (String com)
	{
		String path=System.getProperty(key);
		File f = new File (path);
		
		if(com.equals("exit"))
			System.exit(0);
		
		if(com.equals("help"))
			new Commands().help(com);
		
		else if(com.equals("-al"))
			for(File tmp : f.listFiles())
				System.out.println(tmp.getName() + "	" + tmp.length() + "	" + tmp.lastModified());
		
		else if (com.equals(""))
			for(File tmp : f.listFiles())
				System.out.println(tmp.getName());

		else
			System.out.println("Invalid Command");
	}

	public void cp (String com)
	{
		String [] arr = com.split(" ");
		String src="", dst="";
		
		if(com.equals("exit"))
			System.exit(0);
		
		if(com.equals("help"))
			new Commands().help(com);
		
		else if(com.charAt(0)!='-' && arr.length==2)
		{
			try
			{
				Files.copy(new File (arr[0]).toPath(), new File (arr[1]).toPath()
						, StandardCopyOption.REPLACE_EXISTING);
			}
			catch (Exception ex)
			{
				System.out.println("Invalid path");
			}
		}
					
		else if (arr[0].equals("-i") && (arr.length==2 || arr.length==3))
		{
			
			if(arr.length==3)
			{
				src=arr[1];
				dst=arr[2];
			}
			
			else if (arr.length==2)
			{
				src=arr[1].substring(1);
				dst = System.getProperty(key) + com.substring(com.lastIndexOf('\\'));
			}
			
			try
			{
				if (new File (dst).exists())
				{
					Scanner input2 = new Scanner (System.in);
					System.out.println("File exists\nDo you want to repalce it\n"
							+ "1-Yes	2-No");
					int x = input2.nextInt();
					
					if(x==1)
						Files.copy(new File (src).toPath(), new File (dst).toPath()
								, StandardCopyOption.REPLACE_EXISTING);					
				}
				else
					Files.copy(new File (src).toPath(), new File (dst).toPath()
							, StandardCopyOption.REPLACE_EXISTING);
			}
			catch (Exception ex)
			{
				System.out.println("Invalid path");
			}
		}
		
		else
			System.out.println("Invalid Command");
	}
	
	public void mv (String com)
	{
		String [] arr = com.split(" ");
		String src="", dst="";
		
		if(com.equals("exit"))
			System.exit(0);
		
		if(com.equals("help"))
			new Commands().help(com);
		
		else if(arr.length==2 || arr.length==3)
		{
			if(arr.length==3)
			{
				src=arr[1];
				dst=arr[2];
			}
			
			else if (arr.length==2)
			{
				src=arr[1].substring(1);
				dst = System.getProperty(key) + com.substring(com.lastIndexOf('\\'));
			}

			try
			{
				Files.move(new File (src).toPath(), new File (dst).toPath()
						, StandardCopyOption.REPLACE_EXISTING);
			}
			catch (Exception ex)
			{
				System.out.println("Invalid path");
			}
		}
		
		else
			System.out.println("Invalid Command");
	}

	public void rm (String com)
	{
		if(com.equals("exit"))
			System.exit(0);
		
		if(com.equals("help"))
		{
			new Commands().help(com);
			return ;
		}
		
		File dir = new File (com.substring(1));
		
		for(File tmp : dir.listFiles())
		{
			if(tmp.isDirectory())
				rm('\\'+tmp.getPath());
			tmp.delete();
		}

		dir.delete();
	}
	
	public void cat (String com)
	{
		
	}
	
	public void more (String com)
	{
		
	}

	public void mk_dir (String com)
	{
		if(com.equals("exit"))
			System.exit(0);
		
		if(com.equals("help"))
			new Commands().help(com);

		else
		{
			File dir = new File (com.substring(1));
			
			if(!dir.exists())
				dir.mkdir();
		}
	}

	public void rmdir (String com)
	{

		if(com.equals("exit"))
			System.exit(0);
		
		if(com.equals("help"))
		{
			new Commands().help(com);
			return ;
		}
			
		File dir = new File (com.substring(1));
		
		if (dir.isDirectory() && dir.list().length==0)
			dir.delete();
		else
		{
			Scanner input3 = new Scanner (System.in);
			System.out.println("File is not empty do you wnat to delete it\n"
					+ "1-Yes	2-No");
			int num = input3.nextInt();
			
			if (num==1)
				new Commands().rm(com);
		}
	}
	
	
}
