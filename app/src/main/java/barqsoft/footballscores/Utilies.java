package barqsoft.footballscores;

import android.content.Context;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilies {
    public static final int SERIE_A = 357;
    public static final int PREMIER_LEGAUE = 354;
    public static final int CHAMPIONS_LEAGUE = 362;
    public static final int PRIMERA_DIVISION = 358;
    public static final int BUNDESLIGA = 351;

    public static int getLeague(int league_num) {
        switch (league_num) {
            case SERIE_A:
                return R.string.seriaa;
            case PREMIER_LEGAUE:
                return R.string.premier_league;
            case CHAMPIONS_LEAGUE:
                return R.string.champions_league;
            case PRIMERA_DIVISION:
                return R.string.primera_divison;
            case BUNDESLIGA:
                return R.string.bundesliga;
            default:
                return R.string.not_known_league;
        }
    }

    public static String getMatchDay(Context context, int match_day, int league_num) {

        if (league_num == CHAMPIONS_LEAGUE) {
            if (match_day <= 6) {
                return context.getString(R.string.matchday_six);
            } else if (match_day == 7 || match_day == 8) {
                return context.getString(R.string.first_knockout_round);
            } else if (match_day == 9 || match_day == 10) {
                return context.getString(R.string.quarter_final);
            } else if (match_day == 11 || match_day == 12) {
                return context.getString(R.string.semi_final);
            } else {
                return context.getString(R.string.final_text);
            }

        } else {
            return context.getString(R.string.matchday_num, match_day);
        }

    }

    public static String getScores(int home_goals, int awaygoals) {
        if (home_goals < 0 || awaygoals < 0) {
            return " - ";
        } else {
            return String.valueOf(home_goals) + " - " + String.valueOf(awaygoals);
        }
    }

    public static int getTeamCrestByTeamName(String teamname) {
        if (teamname == null) {
            return R.drawable.no_icon;
        }
        switch (teamname) { //This is the set of icons that are currently in the app. Feel free to find and add more
            //as you go.
            case "Arsenal FC":
                return R.drawable.arsenal;
            case "Manchester United FC":
                return R.drawable.manchester_united;
            case "Swansea City":
                return R.drawable.swansea_city_afc;
            case "Leicester City":
                return R.drawable.leicester_city_fc_hd_logo;
            case "Everton FC":
                return R.drawable.everton_fc_logo1;
            case "West Ham United FC":
                return R.drawable.west_ham;
            case "Tottenham Hotspur FC":
                return R.drawable.tottenham_hotspur;
            case "West Bromwich Albion":
                return R.drawable.west_bromwich_albion_hd_logo;
            case "Sunderland AFC":
                return R.drawable.sunderland;
            case "Stoke City FC":
                return R.drawable.stoke_city;
            case "Aston Villa FC":
                return R.drawable.aston_villa;
            //case "": return R.drawable.burney_fc_hd_logo;
            case "Chelsea FC":
                return R.drawable.chelsea;
            case "Crystal Palace FC":
                return R.drawable.crystal_palace_fc;
            //case "": return R.drawable.hull_city_afc_hd_logo;
            case "Liverpool FC":
                return R.drawable.liverpool;
            case "MSV Duisburg":
                return R.drawable.msv_duisburg;
            case "Karlsruher SC":
                return R.drawable.karlsruher_sc;
            case "SV Sandhausen":
                return R.drawable.sv_sandhausen;
            case "1. FC Heidenheim 1846":
                return R.drawable.one_fc_heidenheim_1846;
            case "Empoli FC":
                return R.drawable.empoli_fc;
            case "Werder Bremen":
                return R.drawable.werder_bremen;
            case "TSG 1899 Hoffenheim":
                return R.drawable.hoffenheim_tsg;
            case "Juventus Turin":
                return R.drawable.juventus;
            case "SSC Napoli":
                return R.drawable.napoli;
            case "VfB Stuttgart":
                return R.drawable.vfb_stuttgart;
            case "Hertha BSC":
                return R.drawable.hertha_berlin;
            case "VfL Wolfsburg":
                return R.drawable.wolfsburg;









            default:
                return R.drawable.ic_no_icon;
        }
    }
}
