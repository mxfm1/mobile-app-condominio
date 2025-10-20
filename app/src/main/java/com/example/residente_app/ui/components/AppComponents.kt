package com.example.residente_app.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.residente_app.R
import com.example.residente_app.ui.theme.Primary
import com.example.residente_app.ui.theme.TextColor
import com.example.residente_app.ui.theme.bgColor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle

@Composable
fun NormalTextComponent(value:String){
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth().heightIn(min=20.dp),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        //color = colorResource(id = R.color.colorText)
        color = TextColor,
        textAlign = TextAlign.Center
    )
}

@Composable
fun HeaderTextComponent(value:String){
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth().heightIn(),
        style = TextStyle(
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ),
        //color = colorResource(id = R.color.colorText)
        color = TextColor,
        textAlign = TextAlign.Center
    )
}

@Composable
fun TextField(
        label:String,
        icon: ImageVector? = null
){
    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth()
            .background(bgColor, shape = RoundedCornerShape(6.dp))
            ,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
        },
        keyboardOptions = KeyboardOptions.Default,
        label = {Text(label)},
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,

        ),
        leadingIcon = {
           icon?.let{
               Icon(
                   imageVector = it,
                   contentDescription = "",
                   tint = TextColor
               )
           }
        }
    )
}

@Composable
fun PasswordField(
    label:String,
    icon: ImageVector
){

    val value = remember {
        mutableStateOf("")
    }

    val isVisible = remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value= value.value,
        onValueChange = {
            value.value = it
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        label = {Text(label)},
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            ),
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = ""
            )
        },
        trailingIcon = {
            val iconImage = if(isVisible.value){
                Icons.Filled.Visibility
            }else{
                Icons.Filled.VisibilityOff
            }

            var description = if(isVisible.value){
                "hide password"
            }else{
                "show password"
            }
            IconButton(onClick = {isVisible.value = !isVisible.value}){
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if(isVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun CheckboxField(label:String,  onTextSelected: (String) -> Unit){
    Row(
        modifier = Modifier.fillMaxWidth()
            .heightIn(56.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        val checkBoxState = remember {
            mutableStateOf(false)
        }

        Checkbox(
            onCheckedChange = {
                checkBoxState.value = !checkBoxState.value
            },
            checked = checkBoxState.value,
        )
        ClickableTextComponent(label, onTextSelected=onTextSelected)
    }
}

@Composable
fun ClickableTextComponent(value:String, onTextSelected: (value:String) -> Unit){
    val initialText = "Al aceptar nuestras "
    val privacyPolicyText = "Políticas de Servicio"
    val andText = " y nuestros "
    val termsAndConditions = "Términos y condiciones"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Primary)){
            pushStringAnnotation(tag = privacyPolicyText, annotation = privacyPolicyText)
            append(privacyPolicyText)
            pop()
        }
        append(andText)
        withStyle(style = SpanStyle(color = Primary)){
            pushStringAnnotation(tag = termsAndConditions, annotation = termsAndConditions)
            append(termsAndConditions)
            pop()
        }
    }

    ClickableText(text = annotatedString, onClick = {offset ->
      annotatedString.getStringAnnotations(offset,offset)
          .firstOrNull()?.also { span ->
              Log.d("Clickable component", "${span}")

             if((span.item == termsAndConditions) || (span.item == privacyPolicyText)){
                 onTextSelected(span.item)
             }
          }
    })
}