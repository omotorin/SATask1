package kz.epam.spadv.repository;

import kz.epam.spadv.domain.Win;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Oleg_Motorin on 29.10.2015.
 */
public class MapWinsRepository implements WinsRepository {

    private Map<Long, Win> wins = new HashMap<>();
    private long id = 0L;

    @Override
    public Win save(Win win) {
        if (win != null) {
            if (win.getId() == null) {
                id++;
                wins.put(id, win);
                win.setId(id);
            } else {
                wins.put(win.getId(), win);
            }
        }
        return win;
    }

    @Override
    public List<Win> getAll() {
        return wins.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<Win> getByUserId(long userId) {
        return wins.values().stream().filter(w -> w.getUser().getId() == userId).collect(Collectors.toList());
    }

    @Override
    public void delete(long userId) {
        wins.values().removeIf(w -> w.getUser().getId() == userId);
    }
}
