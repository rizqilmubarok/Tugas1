import java.net.*;
        import java.util.Scanner;
    public class Detector_IP {
        public static void main(String[] args) {
            Scanner scan = new Scanner(System.in);

            System.out.print("Masukkan URL : ");
            String domain = scan.nextLine();

            try {
                InetAddress inet = InetAddress.getByName(domain);
                String ip = inet.getHostAddress();

                System.out.println("\n--- HASIL DETEKSI ---");
                System.out.println("URL        : " + domain);
                System.out.println("IP Address : " + ip);

                // Pisahkan IP menjadi oktet
                String[] bagi = ip.split("\\.");
                int[] oktet = new int[bagi.length];

                for (int i = 0; i < bagi.length; i++) {
                    oktet[i] = Integer.parseInt(bagi[i]);
                }

                int first = oktet[0];

                String kelas = tentukanKelas(first);
                System.out.println("Kelas IP   : " + kelas);

                tampilkanNetHost(kelas, oktet);

                System.out.println("\nKonversi Biner per Oktet:");
                for (int o : oktet) {
                    System.out.println(o + " -> " + toBinary(o));
                }

            } catch (Exception e) {
                System.out.println("Domain tidak valid atau IP tidak dapat ditemukan.");
            }
        }
        static String tentukanKelas(int pertama) {
            if (pertama >= 1 && pertama <= 126) return "A";
            if (pertama == 127) return "Loopback (khusus)";
            if (pertama >= 128 && pertama <= 191) return "B";
            if (pertama >= 192 && pertama <= 223) return "C";
            if (pertama >= 224 && pertama <= 239) return "D (Multicast)";
            if (pertama >= 240 && pertama <= 255) return "E (Eksperimental)";
            return "Tidak diketahui";
        }
        static void tampilkanNetHost(String kelas, int[] o) {
            System.out.println("\n--- Net ID & Host ID ---");

            switch (kelas) {
                case "A":
                    System.out.println("Net ID  : " + o[0]);
                    System.out.println("Host ID : " + o[1] + "." + o[2] + "." + o[3]);
                    break;

                case "B":
                    System.out.println("Net ID  : " + o[0] + "." + o[1]);
                    System.out.println("Host ID : " + o[2] + "." + o[3]);
                    break;

                case "C":
                    System.out.println("Net ID  : " + o[0] + "." + o[1] + "." + o[2]);
                    System.out.println("Host ID : " + o[3]);
                    break;

                case "D (Multicast)":
                case "E (Eksperimental)":
                    System.out.println("Kelas D dan E tidak memiliki NetID/HostID.");
                    break;

                default:
                    System.out.println("Tidak dapat menentukan NetID/HostID.");
            }
        }
        static String toBinary(int nilai) {
            return String.format("%8s", Integer.toBinaryString(nilai)).replace(' ', '0');
        }
    }