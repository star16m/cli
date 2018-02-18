select
    *
from
    tb_haha
where
    exits (
        select
            1
        from
            tb_tt
        where
            tb_haha.haha = tb_tt.haha
    )
    and 1 = 2
    AND CASE WHEN 1 = 2 THEN 2 END = 2