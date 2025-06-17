package com.example.product.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "products_in_cart",
    foreignKeys = [
        ForeignKey(
            entity = ProductDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["product_id"],
            onDelete = ForeignKey.NO_ACTION
        )
    ],
    indices = [Index("product_id")])
data class ProductInCartDbEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,
    @ColumnInfo("product_id")
    val product: Int,
    @ColumnInfo("date")
    val date: Date?
)
