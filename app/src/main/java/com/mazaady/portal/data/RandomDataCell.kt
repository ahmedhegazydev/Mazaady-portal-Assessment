package com.mazaady.portal.data

import ph.ingenuity.tableview.feature.sort.Sortable

class RandomDataCell(
            _data: Any,
            _id: String = _data.hashCode().toString()
    ) : Sortable {
        
        override var id: String = _id
    
        override var content: Any = _data
    }