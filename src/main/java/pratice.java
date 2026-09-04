public class pratice {
    public static int factorial(int input) {
        if(input==1) return 1;
        return factorial(input-1)*input;
    }

    public static boolean isPrime(int num) {
        if(num <= 1) return false;
        for (int i = 2 ; i < num ; i++) {
            if(num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
//        System.out.println("Factorial of 5 is : "+factorial(5));
//        int a = 5;
//        int ans = 1;
//        for (int i = 1 ; i <= a ; i++) {
//            ans = ans * i;
//        }
//        System.out.println("Factorial of " + a + " : " + ans);

//        2 Prime Number
//        int num = 3;
//        boolean ans = isPrime(num);
//        if (ans) {
//            System.out.println("Number is Prime");
//        } else {
//            System.out.println("Number is not Prime");
//        }

//        3 list of prime numbers between 1 - 100
//        for (int i = 1 ; i <= 100 ; i++) {
//            boolean ans = isPrime(i);
//            if (ans) {
//                System.out.println(i + " is a Prime Number.");
//            }
//        }

//        4 List of Even Odd
//        for (int i = 1 ; i <= 100 ; i++) {
//            if(i % 2 == 0) {
//                System.out.println(i+" is Even Number.");
//            } else {
//                System.out.println(i+" is Odd Number.");
//            }
//        }
        int[] array = {1,2,3,4};
    }
}