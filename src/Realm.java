import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Realm{
    private static BufferedReader br;
    private static FantasyCharacter player = null;
    private static BattleScene battleScene = null;

    public static void main(String[] args) {
        br = new BufferedReader(new InputStreamReader(System.in));
        battleScene = new BattleScene();
        System.out.println("������� ��� ���������");
        try {
            command(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void command(String string) throws IOException {
        if (player == null) {
            player = new Hero(string,
                    100,
                    20,
                    20,
                    0,
                    0
            );
            System.out.println(String.format("������ ��� ��� �� �������� �������� %s! �� ����� ��� ����� ������!", player.getName()));
            printNavigation();
        }
        switch (string) {
            case "1": {
                System.out.println("�������� ��� �� �������");
                command(br.readLine());
            }
            break;
            case "2": {
                commitFight();
            }
            break;
            case "3": {
                System.exit(1);
            }
            break;
            case "��":
                command("2");
                break;
            case "���": {
                printNavigation();
                command(br.readLine());
            }

        }
        command(br.readLine());
    }

    private static void printNavigation() {
        System.out.println("���� �� ������ �����?");
        System.out.println("1. � ��������");
        System.out.println("2. � ����� ���");
        System.out.println("3. �����");
    }

    private static void commitFight() {
        battleScene.fight(player, createMonster(), new FightCallback() {
            @Override
            public void fightWin() {
                System.out.println(String.format("%s �������! ������ � ��� %d ����� � %d ������, � ����� �������� %d ������ ��������.", player.getName(), player.getXp(), player.getGold(), player.getHealthPoints()));
                System.out.println("������� ���������� ����� ��� ��������� � �����? (��/���)");
                try {
                    command(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fightLost() {

            }
        });
    }
    private static FantasyCharacter createMonster(){
        int random = (int) (Math.random() * 10);
        if(random % 2 == 0) return new Goblin(
                "������",
                50,
                10,
                10,
                100,
                20
        );
        else return new Skeleton(
                "������",
                25,
                20,
                20,
                100,
                10
        );
    }

}
