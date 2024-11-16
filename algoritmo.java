import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class algoritmo {

    private static final Logger logger = Logger.getLogger(algoritmo.class.getName());

    public static void main(String[] args) throws Exception {

        // Configurazione del logger
        try {
            // Aggiungo un handler per la console
            ConsoleHandler consoleHandler = new ConsoleHandler();
            logger.addHandler(consoleHandler);
            logger.setLevel(Level.INFO);

            // Aggiungo un handler per il file di log
            FileHandler fileHandler = new FileHandler("rsa_log.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            logger.info("Avvio dell'algoritmo RSA");

            BigInteger p, q, n, eulero, p1, q1, e, d, m, c;
            SecureRandom random = new SecureRandom();

            // Genero numeri primi p e q
            logger.info("Generazione dei numeri primi p e q");
            p = BigInteger.probablePrime(512, random);
            q = BigInteger.probablePrime(512, random);

            // Calcolo n e la funzione di Eulero
            n = p.multiply(q);
            p1 = p.subtract(BigInteger.ONE);
            q1 = q.subtract(BigInteger.ONE);
            eulero = p1.multiply(q1);

            logger.info("n (p * q) calcolato: " + n);
            logger.info("Funzione di Eulero calcolata: " + eulero);

            // Trovo e e calcolo d
            do {
                e = new BigInteger(10, random);
            } while (e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(eulero) >= 0 || eulero.gcd(e).compareTo(BigInteger.ONE) != 0);
            d = e.modInverse(eulero);

            logger.info("Chiavi pubblica e privata generate.");
            logger.info("e (chiave pubblica): " + e);
            logger.info("d (chiave privata): " + d);

            Scanner in = new Scanner(System.in);
            System.out.println("Inserisci il messaggio");
            String s = in.nextLine();

            // Trasformo il messaggio in BigInteger
            m = new BigInteger(s.getBytes());
            logger.info("Messaggio in chiaro: " + s);

            // Cifro il messaggio
            c = m.modPow(e, n);
            logger.info("Messaggio cifrato: " + c);

            // Decifro il messaggio
            m = c.modPow(d, n);
            logger.info("Messaggio decifrato: " + m);

            // Trasformo il messaggio decifrato in stringa
            String decifrato = new String(m.toByteArray());
            System.out.println("Messaggio: " + decifrato);
            logger.info("Messaggio decifrato (stringa): " + decifrato);

            in.close();

        } catch (Exception e) {
            logger.severe("Errore durante l'esecuzione: " + e.getMessage());
        }
    }
}
