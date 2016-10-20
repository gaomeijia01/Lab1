
import java.util.Scanner;
public class Expression {
   public static void main(String [] args)
   {
	   Expression test = new Expression();
	  
	   System.out.println("请输入正确的表达式：");
	   Scanner sc=new Scanner(System.in);
	   String expre = sc.nextLine();
	   String command =  new String();
	   int f = test.expression(expre);
	   
	   while(f==0)
	   { 
		       
			   System.out.println("请输入正确表达式！");
			   expre = sc.nextLine();
			   f = test.expression(expre);
	   }
	   System.out.println("请输入命令（求值：!simplify var1=num1 ... varn=numn；  求导： !d/d var");
	   command=sc.nextLine();
	   while(command!=null){
		   if(command.charAt(1)=='s')
		   {
			  System.out.println(test.simplify(expre,command));
		   }
		   else
		   {
			   test.derivative(expre,command);
		   }
		   System.out.println("是否想继续对输入字符串进行操作 ？（Y or N）");
		   String str = sc.nextLine();
		   if(str.charAt(0)=='Y' || str.charAt(0)=='y'){
			   System.out.println("请继续输入操作：");
			   command = sc.nextLine();
		   }else {
			   command = null;
			   
		   }
		   
	   }
	   
	   
   }
   private int  expression(String expre)
   {
	  int i,flag=1; 
	  for(i=1;i<expre.length()-1;i++)
	  {
		  char ch = expre.charAt(i);
		  if(!Character.isDigit(ch)&&!Character.isLetter(ch)&&(ch!='+')&&(ch!='*')){
			  flag=0;
			  break;
		  }
		  if(Character.isDigit(ch)){
			  char ch1 = expre.charAt(i-1);
			  char ch2 = expre.charAt(i+1);
			  if((!Character.isDigit(ch1) && ch1 != '+' && ch1 !='*') || (!Character.isDigit(ch2) && ch2 != '+' && ch2 !='*')){
				  flag=0;
			  }
		  }else if(Character.isLetter(ch)){
			  char ch1 = expre.charAt(i-1);
			  char ch2 = expre.charAt(i+1);
			  if((ch1 != '+' && ch1 !='*') || (ch2 != '+' && ch2 !='*')){
				  flag=0;
			  }
		  }else{
			 char ch1 = expre.charAt(i-1);
			 char ch2 = expre.charAt(i+1);
			 if((!Character.isDigit(ch1)&&!Character.isLetter(ch1))||(!Character.isDigit(ch2)&&!Character.isLetter(ch2))){
				 flag=0;
			 }
		  }
	  }
	  if(!Character.isDigit(expre.charAt(0)) && !Character.isLetter(expre.charAt(0))){
			  flag = 0;
		  }
	  if(!Character.isDigit(expre.charAt(expre.length()-1)) && !Character.isLetter(expre.charAt(expre.length()-1))){
		  flag = 0;
	  }
	  return flag;
	  
   }

   private String simplify(String expre,String com)  //对函数进行简化，先把表达式通过‘+’分成几部分，然后分别对每部分进行操作；又将每部分的变量和数字分开对其进行计算后再合并输出
   {
	   int i,j,n=0,k;
	   String ch = new String();
	   
	  
	   for(i=0;i<com.length();i++)
	   {
		   String str=new String();
		   String ch1=new String();
		   if(com.charAt(i) == '=')
		   {
			   
			   ch1=ch1+com.charAt(i-1);
			   while(Character.isDigit(com.charAt(i+1))){
				   ch=com.substring(i+1,i+2);
				   str=str+ch;
				   i=i+1;
				   if(i==com.length()-1){
					   break;
				   }
			   }
			   expre=expre.replace(ch1, str);
		     }
	  }
	   n=0;
	  for(i=0;i<expre.length();i++)
	  {
		  if(expre.charAt(i)=='+'){
			  n=n+1;
		  }
	  }
	  String listn[] = new String[n+1];
	  String listc[] = new String[n+1];
	  int s=0;
	  j=0;
	  for(i=0;i<expre.length();i++)
	  {
		  if(expre.charAt(i)=='+'){
			  listn[j] = expre.substring(s,i);
			  j=j+1;
			  s=i+1;
		  }
	  }
	  listn[n] = expre.substring(s,expre.length()); 
	  for(i=0;i<=n;i++){
		  listc[i] = "*";
	  }
	  int num[ ]=new int[n+1];
	  for(i=0;i<=n;i++){
		  num[i]=1;
	  }
	
	  for(i=0;i<=n;i++)
	  {
		  for(j=0;j<listn[i].length();j++)
		  {
			  if(Character.isDigit(listn[i].charAt(j))){
				  for(k=j;k<listn[i].length();k++){
					  if(listn[i].charAt(k) =='*'){
						  break;
					  }
					  
				  }
				  
				  String digit = listn[i].substring(j,k);
				  num[i] = num[i]*(Integer.parseInt(digit));
				  j=k-1;
			  }
			  else if(Character.isLetter(listn[i].charAt(j))){
				  listc[i] = listc[i]+listn[i].substring(j,j+1)+"*";
				  
			  }
			 
		  }
	  }
	  for(i=0;i<n;i++){
		  String str3 = listc[i];
		  for(j=i+1;j<=n;j++)
		  {
			  if(listc[j] == str3){
				  num[i]=num[i]+num[j];
				  num[j]=0;
				  listc[j] = null;
			  }
		  }
	  }
	  String expre1=new String();
	  for(i=0;i<=n;i++){
		  if(num[i]!=0){
			  expre1=expre1+num[i]+listc[i]+"+";
		  }
	  }
	  expre1=expre1.replace("*+", "+");
	  if(expre1.charAt(expre1.length()-1)=='+'){
		  expre1=expre1.substring(0,expre1.length()-1);
	  }
	  return expre1;
   } 
   private void derivative(String expre,String com){ //只是对以上函数进行的一点改变，先把表达式进行简化，然后再把其通过相同的方法分成几部分分别操作，然后进行降幂求导
	   Expression test = new Expression();
	   expre = test.simplify(expre, com);
	   int i,j,n,flag=0,k;
	   n=0;
	   char ch='*';
	   for(i=0;i<com.length()-1;i++){
		   if(com.charAt(i)=='/'){
			   ch = com.charAt(i+2);
			   flag=1;
			   for(j=0;j<=expre.length();j++){
				   if(expre.charAt(j)==ch){
					   flag=1;
					   break;
				   }
			   }
		   }
	   }
	   if(flag==0){
		   System.out.println("请重新输入！");
	   }else{
		   for(i=0;i<expre.length();i++){
				  if(expre.charAt(i)=='+'){
					  n=n+1;
				  }
		  }
		  String listn[] = new String[n+1];
		  String listc[] = new String[n+1];
		  for(i=0;i<=n;i++){
			  listc[i]="";
		  }
		  int s=0;
		  j=0;
		  for(i=0;i<expre.length();i++)
		  {
			  if(expre.charAt(i)=='+'){
				  listn[j] = expre.substring(s,i);
				  j=j+1;
				  s=i+1;
			  }
		  }
		  listn[n] = expre.substring(s,expre.length()); 
		  int num[ ]=new int[n+1];
		  
		  for(i=0;i<=n;i++){
			  num[i]=1;
		  }
		  int sum[ ]=new int[n+1];
		  for(i=0;i<=n;i++)
		  {
			  sum[i]=0;
			  for(j=0;j<listn[i].length();j++){
				  if(listn[i].charAt(j)==ch){
					  sum[i]=sum[i]+1;           //计算每组变量的次幂
				  }
			  }
			  for(j=0;j<listn[i].length();j++)
			  {
				  if(Character.isDigit(listn[i].charAt(j))){
					  for(k=j;k<listn[i].length();k++){
						  if(listn[i].charAt(k) =='*'){
							  break;
						  }
						  
					  }
					  
					  String digit = listn[i].substring(j,k);
					  num[i] = num[i]*(Integer.parseInt(digit));
					  j=k-1;
				  }
				  
				  else if(Character.isLetter(listn[i].charAt(j))){
					  if(listn[i].charAt(j)!=ch){
						  listc[i] = listc[i]+listn[i].substring(j,j+1)+"*";
					  }
					  
				  }
				  
			  }
			  if(sum[i]!=0 && sum[i]!=1){
					 for(j=1;j<sum[i];j++){
						 listc[i]=listc[i]+"*"+ch+"*";
						
					 }
					 num[i]=num[i]*sum[i];
				 }else if(sum[i]==0){
					 listc[i]=null;
					 num[i]=0;
				 }else{
					 listc[i]=listc[i];
				 }
				 
		  }
		  String expre1=new String();
		  for(i=0;i<=n;i++){
			  if(num[i]!=0){
				  expre1=expre1+num[i]+listc[i]+"+";
			  }
		  }
		  expre1=expre1.replace("*+", "+");
		  if(expre1.charAt(expre1.length()-1)=='+'){
			  expre1=expre1.substring(0,expre1.length()-1);
		  }
		  System.out.println(expre1);
		
	   }
	   
		   
   }
   
}
