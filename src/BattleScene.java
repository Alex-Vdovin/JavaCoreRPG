public class BattleScene {
    public void fight(FantasyCharacter hero, FantasyCharacter monster, FightCallback fightCallback){
        Runnable runnable = () -> {
            int turn = 1;
            boolean isFightEnded = false;
            while (!isFightEnded){
                System.out.println("---���: " + turn + "---");
                if(turn++ %2 != 0){
                    isFightEnded = makeHit(monster, hero, fightCallback);
                }else {
                    isFightEnded = makeHit(hero, monster, fightCallback);
                }
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
    private boolean makeHit(FantasyCharacter defender, FantasyCharacter attacker, FightCallback fightCallback){
        int hit = attacker.attack();
        int defenderHealth = defender.getHealthPoints() - hit;
        if(hit != 0){
            System.out.printf("%s ���� ���� � %d ������!%n", attacker.getName(), hit);
            System.out.printf("� %s �������� %d ������ ��������%n", defender.getName(), defender.getHealthPoints());
        }else {
            System.out.printf("%s �����������!%n", attacker.getName());
        }
        if(defenderHealth <= 0 && defender instanceof Hero){
            System.out.println("��������, �� ���� � ���...");
            fightCallback.fightLost();
            return true;
        }else if(defenderHealth <= 0){
            System.out.printf("���� ��������! �� ��������� %d ����� � %d ������", defender.getXp(), defender.getGold());
            attacker.setXp(attacker.getXp() + defender.getXp());
            attacker.setGold(attacker.getGold() + defender.getGold());
            fightCallback.fightWin();
            return true;
        }else {
            defender.setHealthPoints(defenderHealth);
            return false;
        }
    }
}
