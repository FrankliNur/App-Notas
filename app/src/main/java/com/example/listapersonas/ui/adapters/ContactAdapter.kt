package com.example.listapersonas.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listapersonas.R
import com.example.listapersonas.models.Contact

class ContactAdapter(
    private val contactList: ArrayList<Contact>,
    private val listener: OnContactClickListener
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    // Lista de colores
    private val colors = arrayOf(
        android.R.color.holo_blue_light,
        android.R.color.holo_green_light,
        android.R.color.holo_red_light,
        android.R.color.holo_orange_light,
        android.R.color.holo_purple,
        android.R.color.darker_gray,
        android.R.color.holo_blue_dark,
        android.R.color.holo_green_dark,
        android.R.color.holo_red_dark,
        android.R.color.holo_orange_dark
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ContactViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.contacto_item_layout, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.bind(contact, listener)

        // Aplicar el color guardado al fondo del layout
        holder.itemView.setBackgroundResource(contact.color)

        // Cambiar color al presionar btnColor
        holder.btnColor.setOnClickListener {
            val randomColor = colors.random() // Escoger un color aleatorio
            contact.color = randomColor // Guardar el color seleccionado en el contacto
            holder.itemView.setBackgroundResource(randomColor) // Aplicar el color
        }

    }

    fun itemAdded(contact: Contact) {
        contactList.add(1, contact)
        notifyItemInserted(1)
//        notifyDataSetChanged()
    }

    fun itemDeleted(contact: Contact) {
        val index = contactList.indexOf(contact)
        contactList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun itemUpdated(contact: Contact) {
        val index = contactList.indexOf(contact)
        contactList[index] = contact
        notifyItemChanged(index)
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var lblContactItemName = itemView.findViewById<TextView>(R.id.lblContactItemName)
        private var lblContactItemPhone = itemView.findViewById<TextView>(R.id.lblContactItemPhone)
        private var btnEditContactItem = itemView.findViewById<ImageButton>(R.id.btnEditContactItem)
        private var btnDeleteContactItem = itemView.findViewById<ImageButton>(R.id.btnDeleteContactItem)
        var btnColor = itemView.findViewById<ImageButton>(R.id.btn_Color) // Acceso al botón


        fun bind(contact: Contact, listener: OnContactClickListener) {

            lblContactItemName.text = contact.titulo
            lblContactItemPhone.text = contact.nota
            btnEditContactItem.setOnClickListener {
                listener.onContactEditClickListener(contact)
            }
            btnDeleteContactItem.setOnClickListener {
                listener.onContactDeleteClickListener(contact)
            }
        }
    }

    public interface OnContactClickListener {
        fun onContactEditClickListener(contact: Contact)
        fun onContactDeleteClickListener(contact: Contact)
    }
}